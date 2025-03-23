-- 1. user 테이블에 관리자 기본 정보 삽입
INSERT INTO `user` (username, username_last_modified, interested_category)
VALUES ('admin', NOW(), 'POLITICS,ECONOMY,SOCIETY');

-- 2. 방금 생성된 user id를 기반으로 user_auth 테이블에 인증 정보 및 관리자 권한 삽입
INSERT INTO `user_auth` (user_id, access_token, access_token_last_modified, refresh_token, refresh_token_last_modified, social_id, user_role)
VALUES (LAST_INSERT_ID(), 'admin_access_token', NOW(), 'admin_refresh_token', NOW(), 'admin_social_id', 'ADMIN');
