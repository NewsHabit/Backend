CREATE TABLE user_daily_goal_log (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     user_id BIGINT NOT NULL,
     daily_goal INT NOT NULL,
     start_date DATE NOT NULL,
     end_date DATE,

     CONSTRAINT fk_user_daily_goal_log_user
         FOREIGN KEY (user_id)
             REFERENCES user(id)
             ON DELETE CASCADE
);
