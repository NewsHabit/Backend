import json
import urllib.request
import logging
from urllib.parse import quote

import CrawlNews

"""
네이버 API를 사용해 관련 기사들을 응답 받는 메서드입니다.
"""
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

"""
네이버 API 로 제공받은 데이터에서 각 기사의 url을 추출하는 메서드입니다.
"""
def getSearchedUrls(news : list) -> list :
    urls = []
    for info in news :
        if "n.news" in info["link"] :
            urls.append(info["link"])
    return urls


"""
네이버 API를 통해 검색어와 관련된 기사 링크를 크롤링하고
필요한 데이터를 기사 링크를 기반으로 추출하는 메서드입니다.
keyword: 타겟 검색어
start: 1 ~ 1000 시작 번호
display: 1 ~ 100 페이지
clientInfo: 네이버 API KEY
"""
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

