import json
import urllib.request
import logging

import utils.db_manager as db_manager

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
    db_manager.saveNews(category, articles)
