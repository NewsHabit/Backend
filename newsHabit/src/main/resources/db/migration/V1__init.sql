CREATE TABLE `news` (
    naver_url varchar(128) NOT NULL,
    title varchar(128) NOT NULL,
    category varchar(128) NOT NULL,
    date_time DATETIME NOT NULL,
    img_link varchar(128) DEFAULT NULL,
    description varchar(256) NOT NULL,
    PRIMARY KEY(naver_url)
)
