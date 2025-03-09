CREATE TABLE `user` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    user_name_last_modified DATETIME NOT NULL,
    interested_category VARCHAR(128) NOT NULL,
    auth_token VARCHAR(255) NOT NULL,
    auth_token_last_modified DATETIME NOT NULL,
    user_role VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_auth_token (auth_token)
);
