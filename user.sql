CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    username_modified_at DATETIME NOT NULL,
    interest_categories VARCHAR(255) NOT NULL,
    social_id VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL
);
