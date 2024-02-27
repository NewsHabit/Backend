import MySQLdb
import logging
import time

import ConfigManager

mysqlConf = ConfigManager.getConfigData("mysql")
logging.getLogger('__main__').info("WAITING....")
time.sleep(5)
try :
	conn = MySQLdb.connect(
		user = mysqlConf["user_id"],
		passwd = mysqlConf["user_password"],
		host = mysqlConf["host"],
		db = mysqlConf["table"]
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

def deleteCategoryNewsByHour(hour : int) -> None :
	try :
		cursor.execute(f"DELETE FROM news WHERE date_time < DATE_SUB(NOW(), INTERVAL {hour} HOUR) AND category != \'HOT\'")
	except Exception as e:
		logging.getLogger('__main__').error(e)
		return None
	conn.commit()

def deleteNewsByCategoryAndHour(category : str, hour : int) -> None :
	try :
		cursor.execute(f"DELETE FROM news WHERE date_time <= DATE_SUB(NOW(), INTERVAL {hour} HOUR) AND category = \'{category}\'")
	except Exception as e:
		logging.getLogger('__main__').error(e)
		return None
	conn.commit()
