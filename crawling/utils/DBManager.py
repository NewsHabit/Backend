import MySQLdb
import logging

import ConfigManager

mysqlConf = ConfigManager.getConfigData("mysql")
logging.getLogger('__main__').info("WAITING....")
try :
	conn = MySQLdb.connect(
		user = mysqlConf["user_id"],
		passwd = mysqlConf["user_password"],
		host = mysqlConf["host"],
		db = mysqlConf["table"],
		charset='utf8mb4'
	)
	cursor = conn.cursor()
except Exception as e :
	logging.getLogger('__main__').error(e)
logging.getLogger('__main__').info("DB CONNECTED!!!")

def saveNews(category : str, newsList : list) -> None :
	for news in newsList :
		try :
			cursor.execute(f"INSERT INTO news VALUES(\"{news.naverLink}\", \"{news.title}\", \
						\"{category}\", \"{news.crawledTime}\", \"{news.imgLink}\", \"{news.description}\")")
		except Exception :
			pass
	conn.commit()

def deleteNewsByCategoryAndHour(category : str, hour : int, targetCnt : int) -> None :
	if isAbleToDelete(category, hour, targetCnt) == False :
		logging.getLogger('__main__').info("TOO FEW TO DELETE")
		return None
	try :
		cursor.execute(f"DELETE FROM news WHERE date_time <= DATE_SUB(NOW(), INTERVAL {hour} HOUR) AND category = \'{category}\'")
	except Exception as e:
		logging.getLogger('__main__').error(e)
		return None
	conn.commit()

def isAbleToDelete(category : str, hour : int, targetCnt : int) -> bool :
	try :
		cursor.execute(f"SELECT COUNT(*) FROM news WHERE date_time BETWEEN DATE_SUB(NOW(), INTERVAL {hour} HOUR) AND NOW() AND category = \'{category}\'")
	except Exception as e:
		logging.getLogger('__main__').error(e)
		return False
	result = cursor.fetchone()
	resultCnt = result[0]
	if resultCnt < targetCnt :
		return False
	return True