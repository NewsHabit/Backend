import requests

import logging
import ConfigManager

def extractRealTimePopularSearches() -> list :
    url = ConfigManager.getConfigData("ranking_site").get("url")
    try :
        response = requests.get(url, headers={"User-Agent": ConfigManager.getConfigData("request_header").get("User-Agent")})
        if response.status_code != 200 :
            raise Exception("headline crawl failed")
    except Exception as e :
        logging.getLogger('__main__').error(e)
        return []
    data = response.json()
    ranking = data["top10"]
    result = []
    for r in ranking :
        result.append(r["keyword"])
    return result