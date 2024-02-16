import json
import logging
import logging.config

# 네이버 api 사용자 정보 가져오는 함수
def getConfigData(data : str) -> map :
	try:
		with open("../config/config.json", 'r') as f :
			jsonData = json.load(f)
	except Exception as e :
		logging.getLogger('__main__').error(e)
		exit(1)
	return jsonData[data]