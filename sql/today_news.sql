CREATE TABLE today_news (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '기본 키(PK)',
    news_id INT NULL COMMENT '뉴스 테이블(news) FK',
    published_at DATE NOT NULL COMMENT '생성 날짜',

    CONSTRAINT fk_today_news_news
        FOREIGN KEY (news_id)
            REFERENCES news(id)
            ON DELETE SET NULL
);
