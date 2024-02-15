import MySQLdb
import logging
import sys
sys.path.append('../')

import utils.Config

mysqlConf = utils.Config.getConfigData("mysql")

conn = MySQLdb.connect(
	user = mysqlConf["user_id"],
	passwd = mysqlConf["user_password"],
	host = "localhost",
	db = mysqlConf["table"]
)

cursor = conn.cursor()

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
