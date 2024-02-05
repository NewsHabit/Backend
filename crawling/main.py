import naver_api
import utils.config

def main():
    clientInfo = utils.config.getConfigData("naver_api")

    categories = ["정치"]# 핫 키워드 검색으로 바꿀듯

    for category in categories :
        naver_api.getNaverSearch(category, 1, 50, clientInfo)

if __name__ == '__main__':
    main()