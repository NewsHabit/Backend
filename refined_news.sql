CREATE TABLE refined_news (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL,
    five_w_one_h TEXT,
    summary TEXT,
    published_at DATETIME NOT NULL,
    news_category ENUM('POLITICS', 'ECONOMY', 'SOCIETY', 'LIFESTYLE_CULTURE', 'WORLD', 'IT_SCIENCE') NOT NULL,
    click_cnt BIGINT DEFAULT 0,
    original_url VARCHAR(255) NOT NULL
);
