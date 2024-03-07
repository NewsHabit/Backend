import MySQLdb
import logging

import ConfigManager

"""
DB 연결
"""
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

"""
뉴스 기사를 DB에 저장하는 메서드입니다.
"""
def saveNews(category : str, newsList : list) -> None :
	for news in newsList :
		try :
			cursor.execute(f"INSERT INTO news VALUES(\"{news.naverLink}\", \"{news.title}\", \
						\"{category}\", \"{news.crawledTime}\", \"{news.imgLink}\", \"{news.description}\")")
		except Exception :
			pass
	conn.commit()

"""
해당 카테고리의 기사를 DB에서 조건에 맞게 삭제하는 메서드입니다.
조건: isAbleToDelete(카테고리, 현재시간에서 몇시간 전까지, 남아있어야하는 개수)

"""
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

"""
해당 카테고리의 (현재 시간 - hour ~ 현재 시간)을 만족하는 타겟 개수가 targetCnt 보다 크다면 삭제하는 조건
"""
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