CREATE TABLE news_read_log (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '기본 키(PK)',
    user_id INT NOT NULL COMMENT 'users 테이블 FK',
    news_id INT NULL COMMENT 'news 테이블 FK',
    category VARCHAR(50) NOT NULL COMMENT '뉴스 카테고리',
    is_today_news BOOLEAN NOT NULL DEFAULT FALSE COMMENT '오늘의 뉴스 여부',
    published_at DATETIME NOT NULL COMMENT '생성 날짜',

    CONSTRAINT fk_nrl_user
        FOREIGN KEY (user_id)
            REFERENCES user(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_nrl_news
        FOREIGN KEY (news_id)
            REFERENCES news(id)
            ON DELETE SET NULL
);
