import requests
from bs4 import BeautifulSoup
import logging
import random
import time
from datetime import datetime

from News import News
import ConfigManager

def extractHeadlineUrl(sid : int, page : int) -> list :
    time.sleep(random.uniform(1, 3))
    ### 뉴스 분야(sid)와 페이지(page)를 입력하면 그에 대한 링크들을 리스트로 추출하는 함수 ###
    ## 1. headline 기사만 추출된다.
    url = f"https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1={sid}#&date=%2000:00:00&page={page}"
    try :
        html = requests.get(url, headers={"User-Agent": ConfigManager.getConfigData("request_header").get("User-Agent")})
        if html.status_code != 200 :
            raise Exception("headline crawl failed")
    except Exception as e :
        logging.getLogger('__main__').error(e)
        return []
    soup = BeautifulSoup(html.text, "lxml")
    headline_tag = soup.find_all('a', attrs={"class": "sa_text_title", "data-clk" : "airscont"})
    ## 2.
    url_set = set()
    for ex in headline_tag:
        if ("href" in ex.attrs) :
            url_set.add(ex["href"])
    return list(url_set)

def extractNewsFromUrl(url : str) -> News :
    time.sleep(random.uniform(1, 3))
    try :
        html = requests.get(url, headers={"User-Agent": ConfigManager.getConfigData("request_header").get("User-Agent")})
        if html.status_code != 200 :
            raise Exception("news data crawl failed")
    except Exception as e :
        logging.getLogger('__main__').error(e)
        return None
    soup = BeautifulSoup(html.text, "lxml")
    news = News()
    # 제목
    meta = soup.find('meta', attrs = {"property" : "og:title"})
    news.title = meta.get("content").replace('\"', '\\\\"').replace('\'', "\'")
    # 사진 링크
    meta = soup.find('meta', attrs = {"property" : "og:image"})
    news.imgLink = meta.get("content")
    # 요약
    meta = soup.find('meta', attrs = {"property" : "og:description"})
    news.description = meta.get("content").replace('\"', '\\\\"').replace('\'', "\'") + "..."
    # 강조 구문
    try :
        news.description = meta.find('strong').get_text() + "..."
    except Exception :
        pass
    # 본문 링크
    meta = soup.find('meta', attrs = {"property" : "og:url"})
    news.naverLink = meta.get("content")
    # 크롤된 시간
    news.crawledTime = datetime.now().strftime("%Y-%m-%dT%H:00:00")
    return news

def extractNewsFromUrlList(urlList : list, maxCnt : int) -> list :
    newsList = []
    for url in urlList :
        if len(newsList) >= maxCnt :
            break
        news = extractNewsFromUrl(url)
        if news != None :
            newsList.append(news)
    return newsList