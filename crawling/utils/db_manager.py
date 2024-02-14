import MySQLdb
import logging
from datetime import datetime, timedelta

import utils.config
import crawl_news

mysqlConf = utils.config.getConfigData("mysql")

conn = MySQLdb.connect(
	user = mysqlConf["user_id"],
	passwd = mysqlConf["user_password"],
	host = "localhost",
	db = mysqlConf["table"]
	# charset="utf-8"
)

cursor = conn.cursor()
# cursor.execute("CREATE TABLE IF NOT EXISTS articles (naver_url varchar(128),title varchar(128),\
#                category varchar(128),pub_date_time DATETIME,img_link varchar(128),description varchar(256),\
#                PRIMARY KEY(naver_url))")

def saveNews(category : str, news : list) -> None :
	for info in news :
		if "naver" in info["link"] :
			news = crawl_news.extractNewsFromUrl(info["link"])
			if news == None : continue
			try :
				cursor.execute(f"INSERT INTO articles VALUES(\"{news.naverLink}\", \"{news.title}\", \
							\"{category}\", \"{news.pubDate}\", \"{news.imgLink}\", \"{news.description}\")")
			except Exception as e:
				# 중복 기사일때
				pass
	conn.commit()

def deleteNews(hour : int) -> None :
	try :
		cursor.execute(f"DELETE FROM articles WHERE pub_date < DATE_SUB(NOW(), INTERVAL {hour} HOUR)")
	except Exception as e:
		logging.getLogger('__main__').error(e)
		return None
	conn.commit()