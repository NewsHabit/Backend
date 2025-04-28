CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '기본 키(PK)',
    username VARCHAR(50) NOT NULL COMMENT '유저명',
    username_modified_at DATETIME NOT NULL COMMENT '유저명 변경일',
    interest_categories VARCHAR(255) NOT NULL COMMENT '관심 카테고리',
    social_id VARCHAR(100) UNIQUE NOT NULL COMMENT '소셜 로그인 아이디',
    role VARCHAR(20) NOT NULL COMMENT '역할'
);
