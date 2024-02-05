import logging
import logging.config
import json

import naver_api
import utils.config

loggerConfig = json.load(open('./config/logger.json'))
logging.config.dictConfig(loggerConfig)

def main():
    # 크롤링 시 일정 랜덤 시간만큼 텀 주기
    # 각 카테고리 뉴스 추출

    # 핫 키워드 추출 후 여기서 검색
    clientInfo = utils.config.getConfigData("naver_api")
    categories = ["정치"]# 핫 키워드 검색으로 바꿀듯
    for category in categories :
        naver_api.getNaverSearch(category, 1, 50, clientInfo)

if __name__ == '__main__':
    main()