import logging
import logging.config
import json
import random
import time

import naver_api
import utils.config
import crawl_news
import crawl_popular_searches as cps

loggerConfig = json.load(open('./config/logger.json'))
logging.config.dictConfig(loggerConfig)

def main():
    # # 각 카테고리 뉴스 추출
    # sids = [100, 101, 102, 103, 104, 105]# 정치, 경제, 사회, 생활/문화, 세계, IT/과학
    # urls = {100 : [], 101 : [], 102 : [], 103 : [], 104 : [], 105 : []}
    # for sid in sids :
    #     time.sleep( random.uniform(2,4) )# 크롤링 시 일정 랜덤 시간만큼 텀 주기
    #     urls[sid] = crawl_news.ex_headline_url(sid, 1)


    # # 핫 키워드 추출 후 여기서 검색
    clientInfo = utils.config.getConfigData("naver_api")
    # categories = ["정치"]# 핫 키워드 검색으로 바꿀듯
    # for category in categories :
    #     
    # crawl_news.extractNewsFromUrl("https://sports.news.naver.com/news?oid=311&aid=0001691221")


    # naver_api.getNaverSearch("정치", 1, 10, clientInfo)
    print(cps.extractRealTimePopularSearches())




if __name__ == '__main__':
    main()