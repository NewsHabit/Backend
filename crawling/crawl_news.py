import requests
import json
from bs4 import BeautifulSoup
from tqdm.notebook import tqdm

from News import News
from utils import refine

def ex_headline_url(sid : int, page : int) -> list :
    ### 뉴스 분야(sid)와 페이지(page)를 입력하면 그에 대한 링크들을 리스트로 추출하는 함수 ###
    ## 1. headline 기사만 추출된다.
    url = f"https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1={sid}#&date=%2000:00:00&page={page}"
    html = requests.get(url, headers={"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) \
                                      AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36"})
    soup = BeautifulSoup(html.text, "lxml")
    headline_tag = soup.find_all('a', attrs={"class": "sa_text_title", "data-clk" : "airscont"})
    ## 2.
    url_set = set()
    for ex in headline_tag:
        if ("href" in ex.attrs) : # href가 있는것만 고르는 것
            url_set.add(ex["href"])
    return list(url_set)

def ex_normal_tag(sid : int, page : int) -> list :
    ### 뉴스 분야(sid)와 페이지(page)를 입력하면 그에 대한 링크들을 리스트로 추출하는 함수 ###
    ## 1. 메인 기사를 추출한다.
    url = f"https://news.naver.com/main/mainNews.naver?sid1={sid}&date=%2000:00:00&page={page}"
    html = requests.get(url, headers={"User-Agent": "Mozilla/5.0"\
    "(Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) "\
    "Chrome/121.0.0.0 Safari/537.36"})

    requestData = requests.get(url, headers={"User-Agent": "Mozilla/5.0"\
    "(Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) "\
    "Chrome/121.0.0.0 Safari/537.36"})

    if requestData.status_code != 200 :
        return []
    airsResult = requestData.json().get('airsResult')
    airsResultJson = json.loads(airsResult).get('result')
    articles = airsResultJson.get(f'{sid}')
    ex_hrefs = []
    for article in articles :
        if int(article['sectionId']) != sid : continue
        ex_hrefs.append(f"https://n.news.naver.com/mnews/article/{article['officeId']}/{article['articleId']}?sid={sid}")
    for h in ex_hrefs :
         print(h)
    return ex_hrefs

def extractNewsFromUrl(url : str) -> News :
    html = requests.get(url, headers={"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) \
                                      AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36"})
    soup = BeautifulSoup(html.text, "lxml")
    news = News()
    # 제목
    meta = soup.find('meta', attrs = {"property" : "og:title"})
    news.title = meta.get("content")
    # 사진 링크
    meta = soup.find('meta', attrs = {"property" : "og:image"})
    news.imgLink = meta.get("content")
    # 요약
    meta = soup.find('meta', attrs = {"property" : "og:description"})
    news.description = meta.get("content") + "..."
    # 강조 구문
    try :
        news.description = meta.find('strong').get_text() + "..."
    except Exception as e :
        pass
    # 본문 링크
    meta = soup.find('meta', attrs = {"property" : "og:url"})
    news.naverLink = meta.get("content")
    # 발행일
    meta = soup.find('span', attrs = {"class" : "media_end_head_info_datestamp_time _ARTICLE_DATE_TIME"})
    try :
        news.pubDate = meta.get("data-date-time").replace(' ', 'T')
    except Exception as e :
        return None
    return news