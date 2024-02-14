import json
import urllib.request
import MySQLdb
import logging

import utils.config
import crawl_news

from urllib.parse import quote

def getRequestUrl(url : str, clientInfo : map) -> str :
    req = urllib.request.Request(url)
    req.add_header("X-Naver-Client-Id", clientInfo["X-Naver-Client-Id"])
    req.add_header("X-Naver-Client-Secret", clientInfo["X-Naver-Client-Secret"])

    try:
        response = urllib.request.urlopen(req)
        if response.getcode() == 200 :
            return response.read().decode('utf-8')
    except Exception as e :
        logging.getLogger('__main__').error(e)
        return None

# start 1 ~ 1000, display 1 ~ 100

def getNaverSearch(category : str, start : int, display : int, clientInfo : map) -> None :
    base = "https://openapi.naver.com/v1/search/news.json"
    parameters = f"?query={quote(category)}&start={start}&display={display}"
    url = base + parameters

    responseDecode = getRequestUrl(url, clientInfo)

    if responseDecode == None :
        logging.getLogger('__main__').error(f"{category} crawl failed")
        return None

    jsonResponse = json.loads(responseDecode)
    articles = jsonResponse["items"]
    # db 에 저장
    mysqlConf = utils.config.getConfigData("mysql")
    conn = MySQLdb.connect(
        user = mysqlConf["user_id"],
        passwd = mysqlConf["user_password"],
        host = "localhost",
        db = mysqlConf["table"]
        # charset="utf-8"
    )

    cursor = conn.cursor()
    # cursor.execute("CREATE TABLE IF NOT EXISTS articles (naver_url varchar(128),title varchar(128),\
    #                category varchar(128),pub_date varchar(128),img_link varchar(128),description varchar(256),\
    #                PRIMARY KEY(naver_url))")

    for article in articles :
        if "naver" in article["link"] :
            news = crawl_news.extractNewsFromUrl(article["link"])
            if news == None : continue
            try :
                cursor.execute(f"INSERT INTO articles VALUES(\"{news.naverLink}\", \"{news.title}\", \
                            \"{category}\", \"{news.pubDate}\", \"{news.imgLink}\", \"{news.description}\")")
            except Exception as e:
                # 중복 기사일때
                pass
    conn.commit()
