import requests

import logging
import utils.Config

def extractRealTimePopularSearches() -> list :
    url = utils.Config.getConfigData("ranking_site").get("url")
    try :
        response = requests.get(url, headers={"User-Agent": utils.Config.getConfigData("request_header").get("User-Agent")})
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