import requests
import json
from bs4 import BeautifulSoup
from tqdm.notebook import tqdm

def ex_headline_url(sid, page):
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

# def re_tag(sid, pages):
#     ### 특정 분야의 100페이지까지의 뉴스의 링크를 수집하여 중복 제거한 리스트로 변환하는 함수 ###
#     re_lst = []
#     for page in tqdm(range(pages)):
#         re_lst.extend(ex_headline_tag(sid, page+1))
#         re_lst.extend(ex_normal_tag(sid, page+1))

#     # 중복 제거
#     re_set = set(re_lst)
#     return list(re_set)

def extractArticleFromUrl(url) :
    html = requests.get(url, headers={"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) \
                                      AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36"})
    soup = BeautifulSoup(html.text, "lxml")
    # 제목
    meta = soup.find('meta', attrs = {"property" : "og:title"})
    title = meta.get("content")
    # 사진 링크
    meta = soup.find('meta', attrs = {"property" : "og:image"})
    imageLink = meta.get("content")
    # 요약
    meta = soup.find('meta', attrs = {"property" : "og:description"})
    description = meta.get("content")
    # 출처 신문사
    meta = soup.find('meta', attrs = {"property" : "og:article:author"})
    source = meta.get("content")
    # 기자
    meta = soup.find('span', attrs = {"class" : "byline_s"})
    author = meta.get_text()
    # 강조 구문
    strong = None
    try :
        strong = meta.find('strong').get_text() #안되는 기사도 있음
    except Exception as e :
        e
    meta = soup.find('article', attrs={"id" : "dic_area"})
    article = meta.get_text()
    # 이미지 아래 글 삭제
    imgSummary = meta.find_all('em', attrs = {"class" : "img_desc"})
    for summary in imgSummary :
        article = article.replace(summary.get_text(), '')
    # 기사 정제
    articleList = article.split('\n')
    for block in articleList :
        if block == "" :
            articleList.remove("")
    article = ""
    for block in articleList :
        article += block + "\n\n"
    
    print("[ title ]")
    print(title)
    print("[ imageLink ]")
    print(imageLink)
    print("[ description ]")
    print(description)
    print("[ strong ]")
    print(strong)
    print("[ article ]")
    print(article)
    print("[ source ]")
    print(source)
    print("[ author ]")
    print(author)
