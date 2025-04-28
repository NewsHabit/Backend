CREATE TABLE bookmark (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '기본 키(PK)',
    user_id INT NOT NULL COMMENT 'users 테이블 FK',
    news_id INT NOT NULL COMMENT 'news 테이블 FK',
    published_at DATETIME NOT NULL COMMENT '생성 날짜',

    CONSTRAINT fk_daily_news_user
        FOREIGN KEY (user_id)
            REFERENCES user(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_daily_news_news
        FOREIGN KEY (news_id)
            REFERENCES news(id)
            ON DELETE CASCADE
);
