import json
import logging
import logging.config
import urllib.request
import MySQLdb

from urllib.parse import quote

loggerConfig = json.load(open('./conf/logger.json'))
logging.config.dictConfig(loggerConfig)
logger = logging.getLogger(__name__)

# 네이버 api 사용자 정보 가져오는 함수
def getConfigData(data):
	try:
		with open("./conf/config.json", 'r') as f :
			jsonData = json.load(f)
	except Exception as e :
		logger.error(e)
	return jsonData[data]


def getRequestUrl(url, clientInfo):
    req = urllib.request.Request(url)
    req.add_header("X-Naver-Client-Id", clientInfo["clientId"])
    req.add_header("X-Naver-Client-Secret", clientInfo["clientSecret"])

    try:
        response = urllib.request.urlopen(req)
        if response.getcode() == 200 :
            return response.read().decode('utf-8')
    except Exception as e :
        logger.error(e)
        return None


def getNaverSearch(category, start, display, clientInfo):
    searchTarget = quote(category)
    base = "https://openapi.naver.com/v1/search/news.json"
    parameters = f"?query={searchTarget}&start={start}&display={display}"
    url = base + parameters

    responseDecode = getRequestUrl(url, clientInfo)

    if responseDecode == None :
        logger.error(f"{searchTarget} crawl failed")
        return None

    jsonResponse = json.loads(responseDecode)
    articles = jsonResponse["items"]
    # db 에 저장
    mysqlConf = getConfigData("mysql")
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




def main():
    clientInfo = getConfigData("naver_api")

    categories = ["정치", "IT", "경제", "세계"]

    for category in categories :
        getNaverSearch(category, 1, 50, clientInfo)

if __name__ == '__main__':
    main()