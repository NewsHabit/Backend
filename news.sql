CREATE TABLE IF NOT EXISTS news (
	naver_url varchar(128),
	title varchar(128),
	category varchar(128),
	date_time DATETIME,
	img_link varchar(128),
	description varchar(256),
	PRIMARY KEY(naver_url)
)