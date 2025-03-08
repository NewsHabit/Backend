CREATE TABLE refined_news (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    who_summary TEXT,
    what_summary TEXT,
    when_summary TEXT,
    why_summary TEXT,
    where_summary TEXT,
    how_summary TEXT,
    summary TEXT,
    keyword VARCHAR(255),
    published_at DATETIME NOT NULL,
    news_category ENUM('POLITICS', 'ECONOMY', 'SOCIETY', 'LIFESTYLE_CULTURE', 'WORLD', 'IT_SCIENCE') NOT NULL,
    click_cnt BIGINT DEFAULT 0,
    original_url VARCHAR(255) NOT NULL
);
