import logging
import datetime
import pytz

"""
시간대를 KST로 설정하는 Formatter를 정의합니다.
"""
class KSTFormatter(logging.Formatter):
	def converter(self, timestamp):
		dt = datetime.datetime.fromtimestamp(timestamp, tz=pytz.UTC)
		kst = dt.astimezone(pytz.timezone('Asia/Seoul'))
		return kst

	def formatTime(self, record, datefmt=None):
		dt = self.converter(record.created)
		if datefmt:
			return dt.strftime(datefmt)
		else:
			return dt.isoformat()