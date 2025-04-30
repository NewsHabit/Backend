CREATE TABLE auth (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '기본 키(PK)',
    user_id INT NOT NULL COMMENT '유저 테이블(user) FK',
    device_id VARCHAR(100) NOT NULL COMMENT '접속 기기',
    refresh_token VARCHAR(1024) NOT NULL COMMENT '리프레시 토큰',
    published_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '생성 날짜',

    CONSTRAINT fk_auth_user
      FOREIGN KEY (user_id)
          REFERENCES user(id)
          ON DELETE CASCADE
);
