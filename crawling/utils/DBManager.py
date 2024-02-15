import MySQLdb
import logging
import sys
sys.path.append('../')

import utils.Config
import src.CrawlNews as CrawlNews

mysqlConf = utils.Config.getConfigData("mysql")

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

def saveNews(category : str, news : list, cnt : int) -> None :
	curCnt = 0
	for info in news :
		if curCnt >= cnt :
			break
		if "naver" in info["link"] :
			news = CrawlNews.extractNewsFromUrl(info["link"])
			if news == None : continue
			try :
				cursor.execute(f"INSERT INTO articles VALUES(\"{news.naverLink}\", \"{news.title}\", \
							\"{category}\", \"{news.pubDate}\", \"{news.imgLink}\", \"{news.description}\")")
				curCnt += 1
			except Exception as e:
				pass
	conn.commit()

def deleteNews(hour : int) -> None :
	try :
		cursor.execute(f"DELETE FROM articles WHERE pub_date < DATE_SUB(NOW(), INTERVAL {hour} HOUR)")
	except Exception as e:
		logging.getLogger('__main__').error(e)
		return None
	conn.commit()