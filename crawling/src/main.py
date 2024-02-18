import logging
import logging.config
import json
import os
os.chdir("/Users/aoleejohn/Desktop/projects/NewsHabit_Backend/crawling/src")
import sys
sys.path.append('../')
sys.path.append('../utils')
sys.path.append('../data')
import src.NaverApi as NaverApi
import utils.ConfigManager as ConfigManager
import utils.DBManager as DBManager
import src.CrawlNews as CrawlNews
import CrawlPopularSearches as cps

loggerConfig = json.load(open('../config/logger.json'))
logging.config.dictConfig(loggerConfig)

def main():
    try :
        # 네이버 api 에 필요한 정보 config.json 에서 가져오기
        clientInfo = ConfigManager.getConfigData("naver_api")

        # 인기 검색어 뉴스 추출
        keywords = cps.extractRealTimePopularSearches()
        for keyword in keywords :
            newsList = NaverApi.getNewsByNaverSearch(keyword, 1, 100, clientInfo)
            DBManager.saveNews("HOT", newsList)
        # 인기 검색어 뉴스 삭제
        DBManager.deleteNewsByCategoryAndHour("HOT", 1)

        # 카테고리 별 기사 추출
        sids = [100, 101, 102, 103, 104, 105]
        categories = {100 : "POLITICS", 101 : "ECONOMY", 102 : "SOCIETY",
                      103 : "LIFESTYLE/CULTURE", 104 : "IT/SCIENCE", 105 : "WORLD"}
        for sid in sids :
            urlList = CrawlNews.extractHeadlineUrl(sid, 1)
            searchMaxCnt = 10 if len(urlList) > 10 else len(urlList)
            newsList = CrawlNews.extractNewsFromUrlList(urlList, searchMaxCnt)
            DBManager.saveNews(categories[sid], newsList)

        # 주어진 시간 이전 카테고리 별 기사 삭제
        DBManager.deleteCategoryNewsByHour(24)
    except Exception as e :
        logging.getLogger('__main__').error(e)

if __name__ == '__main__':
    main()