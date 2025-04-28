CREATE TABLE user_daily_goal_log (
     id INT AUTO_INCREMENT PRIMARY KEY COMMENT '기본 키(PK)',
     user_id INT NOT NULL COMMENT '유저 테이블(user) FK',
     daily_goal INT NOT NULL COMMENT '일일 목표 수',
     start_date DATE NOT NULL COMMENT '시작일',
     end_date DATE DEFAULT NULL COMMENT '종료일',

     CONSTRAINT fk_user_daily_goal_log_user
         FOREIGN KEY (user_id)
             REFERENCES user(id)
             ON DELETE CASCADE
);
