CREATE TABLE `user` (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    username_last_modified DATETIME NOT NULL,
    interested_category VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);
