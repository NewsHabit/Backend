CREATE TABLE `user_auth` (
    user_id BIGINT NOT NULL,
    access_token VARCHAR(255) NOT NULL,
    access_token_last_modified DATETIME NOT NULL,
    refresh_token VARCHAR(255) NOT NULL,
    refresh_token_last_modified DATETIME NOT NULL,
    social_id VARCHAR(255) NOT NULL,
    user_role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id),
    CONSTRAINT fk_user
     FOREIGN KEY (user_id)
         REFERENCES `user`(id)
         ON DELETE CASCADE
         ON UPDATE CASCADE
);
