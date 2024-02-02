import json
import logging
import logging.config
import urllib.request
from urllib.parse import quote

config = json.load(open('./conf/logger.json'))
logging.config.dictConfig(config)
logger = logging.getLogger(__name__)

def getNaverAuthData():
	try:
		with open("./conf/naver_api_conf.json", 'r') as f :
			jsonData = json.load(f)
	except Exception as e :
		logger.error(e)
	return {"clientId" : jsonData["clientId"], "clientSecret" : jsonData["clientSecret"]}


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


def getNaverSearch(searchTarget, start, display, clientInfo):
    base = "https://openapi.naver.com/v1/search/news.json"
    parameters = f"?query={searchTarget}&start={start}&display={display}"
    url = base + parameters

    responseDecode = getRequestUrl(url, clientInfo)

    if responseDecode == None :
        return None
    else:
        return json.loads(responseDecode)


def main():
    clientInfo = getNaverAuthData()
    searchTarget = quote('정치')

    jsonResponse = getNaverSearch(searchTarget, 1, 100, clientInfo)
    print(jsonResponse)



if __name__ == '__main__':
    main()