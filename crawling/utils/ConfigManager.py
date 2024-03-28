import json
import logging
import logging.config

"""
외부로 노출되면 안되는 config를 가져오는 메서드입니다.
"""
def getConfigData(data : str) -> map :
	try:
		with open("./config/config.json", 'r') as f :
			jsonData = json.load(f)
	except Exception as e :
		logging.getLogger('__main__').error(e)
		exit(1)
	return jsonData[data]