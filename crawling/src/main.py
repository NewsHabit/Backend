import logging
import logging.config
import json
import random
import time
import sys
sys.path.append('../')

import NaverApi
import utils.Config
import CrawlNews
import CrawlPopularSearches as cps

loggerConfig = json.load(open('../config/logger.json'))
logging.config.dictConfig(loggerConfig)

def main():
    # 네이버 api 에 필요한 정보 config.json 에서 가져오기
    clientInfo = utils.Config.getConfigData("naver_api")

    # 인기 검색어 뉴스 추출
    keywords = cps.extractRealTimePopularSearches()
    for keyword in keywords :
        NaverApi.getNaverSearch(keyword, 1, 10, clientInfo)
        break

    # # 핫 키워드 추출 후 여기서 검색
    # categories = ["정치"]# 핫 키워드 검색으로 바꿀듯
    # for category in categories :
    #     
    # CrawlNews.extractNewsFromUrl("https://sports.news.naver.com/news?oid=311&aid=0001691221")


    # NaverApi.getNaverSearch("정치", 1, 10, clientInfo)
    # print(cps.extractRealTimePopularSearches())




if __name__ == '__main__':
    main()