# <img src="https://github.com/Green-Tea-organization/NewsHabit_Backend/assets/100538007/2c35e329-6332-44d5-bf72-861ecfaf8223" width="30" height="30"/> NewsHabit 백엔드 디렉토리입니다.



##  프로젝트 소개
> *뉴스를 잘 접하지 않는 사용자들을 위해 가볍게 접할 수 있도록 추천 뉴스를 제공하는 iOS 애플리케이션입니다.*

## 프로젝트 개발 기간
`2024.02.10 ~ 2024.03.`
## 프로젝트 관리
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"></a>
## 기술 스택
<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white"></a>
<img src="https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/DOCKER-007396?style=for-the-badge&logo=DOCKER&logoColor=white">

<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"></a>
<img src="https://img.shields.io/badge/spring_boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/spring_security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">

<img src="https://img.shields.io/badge/amazon_aws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"></a>

## 프로젝트 아키텍쳐
![AWS 아키텍쳐](https://github.com/Green-Tea-organization/NewsHabit_Backend/assets/100538007/4e681c82-5d41-423a-86ad-38755746e872)

## 기능 소개
### **Crawling**

- Naver Open API를 사용하여 검색어를 기반으로 뉴스에 대한 정보를 얻습니다.
- 추가로 필요한 데이터는 BeautifulSoup 을 사용하여 정적 크롤링을 하였습니다.

### **Spring Boot**

- RESTful API 를 제공하며, thymeleaf 를 통한 정적 리소스를 제공합니다.
- JPA 를 활용하여 RDS 데이터를 사용합니다.
- Spring Security를 활용해 정해진 API에 대해서만 응답을 하여 안정성을 높였습니다.
- Exception Handler를 사용하여 Exception에 유연하게 대처하였습니다.
- 통합테스트를 통해 서비스의 안정성을 높였습니다.

### **API**

- 현재 시간을 기준으로 최근 인기 급상승 중인 뉴스의 간단한 정보를 제공합니다.
- 선택한 카테고리들 중 원하는 수만큼의 기사를 랜덤하게 제공합니다.

### **AWS**

- EC2 인스턴스
    - Docker를 활용하여 Python 스크립트(크롤링)와 Spring Boot 컨테이너를 실행합니다.
- RDS
    - MySQL을 이용하며 보안그룹을 통해 EC2 인스턴스와만 통신하여 데이터 안전성을 높였습니다.
- Route 53
    - 사용자의 인터넷 트래픽을 적합한 인프라 자원으로 라우팅합니다.
- CloudFront & ACM
    - CloudFront를 통해 클라이언트로부터의 모든 요청을 HTTPS로 리다이렉트하도록 설정하여 보안을 강화하였습니다.
    - 원본 인스턴스와 HTTP 통신을 하여 네트워크 오버헤드를 줄이고, 서버의 리소스 사용을 최적화 하였습니다.




## 🔗 관련 링크
[NewsHabit_iOS 디렉토리](https://github.com/Green-Tea-organization/NewsHabit_iOS)

[NewsHabit 소개 페이지](https://newshabit.org)

[블로그](https://songs4ri.vercel.app/news-habit-project)

<details>
  <summary>필요 파일</summary>
  
  > /crawling/config/config.json
  ```json
  {
  	"naver_api" : {
  		"X-Naver-Client-Id" : "네이버 오픈 API ID",
  		"X-Naver-Client-Secret" : "네이버 오픈 API Secret Key"
  	},
  	"mysql" : {
  		"user_id" : "User Id",
  		"user_password" : "User PW",
  		"host" : "endPoint",
  		"table" : "테이블명"
  	},
  	"ranking_site" : {
  		"url" : "실시간 인기 검색어 제공 사이트"
  	},
  	"request_header" : {
  		"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36"
  	}
  }
  ```
  
  > /newsHabit/build/libs
  
  newsHabit-0.0.1-SNAPSHOT.jar 빌드 파일 필요
  
  > /docker-compose.yml
  
  ```yml
  version: '3'
  services:
    mysql:
      image: mysql:latest
      container_name: mysql
      environment:
        MYSQL_DATABASE: 'DataBase'
        MYSQL_USER: 'User Id'
        MYSQL_PASSWORD: 'User PW'
        MYSQL_ROOT_PASSWORD: 'root PW'
        TZ: 'Asia/Seoul'
        MYSQL_CHARSET: utf8mb4
        MYSQL_COLLATION: utf8mb4_unicode_ci
      ports:
        - "3306:3306"
      volumes:
        - db-data:/var/lib/mysql
        - ./my.cnf:/etc/mysql/conf.d/custom.cnf
  
    spring-app:
      build:
        context: ./newsHabit
        dockerfile: dockerfile
      restart:
        on-failure
      ports:
        - "8080:8080"
      environment:
        SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/news_habit?useUnicode=true&characterEncoding=utf8
        SPRING_DATASOURCE_USERNAME: "User Id"
        SPRING_DATASOURCE_PASSWORD: "User PW"
        TZ: "Asia/Seoul"
      depends_on:
        - mysql
  
    python-app:
      build:
        context: ./crawling
        dockerfile: Dockerfile
      environment:
        TZ: "Asia/Seoul"
      depends_on:
        - mysql
  
  volumes:
    db-data:
  ```

</details>
