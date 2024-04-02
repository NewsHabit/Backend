import MySQLdb
import logging
from datetime import datetime, timedelta
import pytz

import ConfigManager

"""
DB 연결
"""
mysqlConf = ConfigManager.getConfigData("mysql")
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
	exit(1)
logging.getLogger('__main__').info("DB CONNECTED!!!")

"""
DB 연결 해제
"""
def disconnectDB() -> None :
	if cursor:
		cursor.close()
	if conn:
		conn.close()
	logging.getLogger('__main__').info("DB DISCONNECTED!!!")

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
	try :
		targetTime = getDeletableTime(category, hour, targetCnt)
		cursor.execute(f"DELETE FROM news WHERE date_time <  \'{targetTime}\' AND category = \'{category}\'")
	except Exception as e:
		logging.getLogger('__main__').error(e)
		return None
	conn.commit()

"""
해당 카테고리의 (현재 시간 - hour ~ 현재 시간)을 만족하는 타겟 개수가 targetCnt 보다 크다면 삭제하는 조건
"""
def getDeletableTime(category : str, hour : int, targetCnt : int) -> str :
	try :
		koreaTimezone = pytz.timezone('Asia/Seoul')
		availableTime = datetime.now(pytz.utc).astimezone(koreaTimezone).replace(minute=0, second=0, microsecond=0)
		targetTime = availableTime
		availableTime = availableTime - timedelta(hours=hour)
		cursor.execute(f"SELECT date_time FROM news WHERE category = \'{category}\' ORDER BY date_time DESC")
		result = cursor.fetchall()

		for r in result :
			if targetCnt <= 0 :
				targetTime = r[0]
				break
			targetCnt -= 1
		targetTime = koreaTimezone.localize(targetTime)
	except Exception as e:
		logging.getLogger('__main__').error(e)
	return min(targetTime, availableTime).strftime("%Y-%m-%dT%H:00:00")