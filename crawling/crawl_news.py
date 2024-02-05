import requests
import json
from bs4 import BeautifulSoup
from tqdm.notebook import tqdm

def ex_headline_tag(sid, page):
    ### 뉴스 분야(sid)와 페이지(page)를 입력하면 그에 대한 링크들을 리스트로 추출하는 함수 ###
    ## 1. headline 기사만 추출된다.
    url = f"https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1={sid}"\
    "#&date=%2000:00:00&page={page}"
    html = requests.get(url, headers={"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) \
                                      AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36"})
    soup = BeautifulSoup(html.text, "lxml")
    a_tag = soup.find_all("a")
    ## 2.
    tag_lst = []
    for a in a_tag:
        if ("href" in a.attrs) and (f"sid={sid}" in a["href"]) and ("article" in a["href"]) : # href가 있는것만 고르는 것
                tag_lst.append(a["href"])
    return tag_lst

def ex_normal_tag(sid, page):
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

def re_tag(sid, pages):
    ### 특정 분야의 100페이지까지의 뉴스의 링크를 수집하여 중복 제거한 리스트로 변환하는 함수 ###
    re_lst = []
    for page in tqdm(range(pages)):
        re_lst.extend(ex_headline_tag(sid, page+1))
        re_lst.extend(ex_normal_tag(sid, page+1))

    # 중복 제거
    re_set = set(re_lst)
    return list(re_set)

# ex_normal_tag(100, 1)
# news = ex_headline_tag(100, 1)