import json
import urllib.request
import MySQLdb
import logging

import utils.config

from urllib.parse import quote

def getRequestUrl(url, clientInfo):
    req = urllib.request.Request(url)
    req.add_header("X-Naver-Client-Id", clientInfo["clientId"])
    req.add_header("X-Naver-Client-Secret", clientInfo["clientSecret"])

    try:
        response = urllib.request.urlopen(req)
        if response.getcode() == 200 :
            return response.read().decode('utf-8')
    except Exception as e :
        logging.getLogger('__main__').error(e)
        return None


def getNaverSearch(category, start, display, clientInfo):
    searchTarget = quote(category)
    base = "https://openapi.naver.com/v1/search/news.json"
    parameters = f"?query={searchTarget}&start={start}&display={display}"
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
    cursor.execute("CREATE TABLE IF NOT EXISTS articles (title text, search text, \
                   origin_url text, naver_url text, pub_date text, contents text)")

    # 입력 데이터 걸러야함
    for article in articles :
        if "naver" in article["link"] :
            title = article["title"]
            originalLink = article["originallink"]
            naverLink = article["link"]
            pubDate = article["pubDate"]
            description = article["description"]
            cursor.execute(f"INSERT INTO articles VALUES(\"{title}\", \"{category}\", \"{originalLink}\", \
                            \"{naverLink}\", \"{pubDate}\", \"내용\")")
    conn.commit()
