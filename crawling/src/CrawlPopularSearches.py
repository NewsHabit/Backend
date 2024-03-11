import requests

import logging
import ConfigManager

"""
실시간 인기 키워드를 크롤하는 메서드입니다.
제대로된 응답을 받지 못한다면 빈 리스트를 반환합니다.
"""
def extractRealTimePopularSearches() -> list :
    url = ConfigManager.getConfigData("ranking_site").get("url")
    try :
        response = requests.get(url, headers={"User-Agent": ConfigManager.getConfigData("request_header").get("User-Agent")})
        if response.status_code != 200 :
            raise Exception("ranking_site crawl failed :" + str(response.status_code))
    except Exception as e :
        logging.getLogger('__main__').error(e)
        return []
    data = response.json()
    ranking = data["top10"]
    result = []
    for r in ranking :
        result.append(r["keyword"])
    return result