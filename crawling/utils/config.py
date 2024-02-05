import json
import utils.logging

# 네이버 api 사용자 정보 가져오는 함수
def getConfigData(data):
	try:
		with open("./config/config.json", 'r') as f :
			jsonData = json.load(f)
	except Exception as e :
		utils.logging.logger.error(e)
	return jsonData[data]