import json
import urllib.request
import logging
import sys
sys.path.append('../')

from urllib.parse import quote
import src.CrawlNews as CrawlNews

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

def getSearchedUrls(news : list) -> list :
    urls = []
    for info in news :
        if "n.news" in info["link"] :
            urls.append(info["link"])
    return urls

def getNewsByNaverSearch(keyword : str, start : int, display : int, clientInfo : map) -> list :
    base = "https://openapi.naver.com/v1/search/news.json"
    parameters = f"?query={quote(keyword)}&start={start}&display={display}"
    url = base + parameters

    responseDecode = getRequestUrl(url, clientInfo)

    if responseDecode == None :
        logging.getLogger('__main__').error(f"getNewsByNaverSearch [{keyword}] crawl failed")
        return None

    jsonResponse = json.loads(responseDecode)
    newsUrls = getSearchedUrls(jsonResponse["items"])
    searchMaxCnt = 1
    newsList = CrawlNews.extractNewsFromUrlList(newsUrls, searchMaxCnt)
    return newsList

