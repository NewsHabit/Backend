CREATE TABLE news (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '기본 키(PK)',
    title VARCHAR(255) NOT NULL COMMENT '제목',
    who_summary VARCHAR(512) COMMENT '누가',
    what_summary VARCHAR(512) COMMENT '무엇을',
    when_summary VARCHAR(512) COMMENT '언제',
    why_summary VARCHAR(512) COMMENT '왜',
    where_summary VARCHAR(512) COMMENT '어디서',
    how_summary VARCHAR(512) COMMENT '어떻게',
    summary VARCHAR(512) COMMENT '요약 내용',
    keyword VARCHAR(255) COMMENT '핵심 키워드',
    published_at DATETIME NOT NULL COMMENT '생성 날짜',
    news_category ENUM('POLITICS', 'ECONOMY', 'SOCIETY', 'LIFESTYLE_CULTURE', 'WORLD', 'IT_SCIENCE') NOT NULL COMMENT '카테고리',
    click_cnt INT DEFAULT 0 NOT NULL COMMENT '조회수',
    original_url VARCHAR(255) NOT NULL COMMENT '원본 링크'
);
