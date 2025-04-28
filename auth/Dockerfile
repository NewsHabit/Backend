# 1) Builder 단계: JDK 기반
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# 1-1) Gradle 래퍼 및 루트 빌드 스크립트 복사 (의존성 캐시 확보)
COPY gradlew .
COPY gradle/ gradle/
COPY settings.gradle .
COPY build.gradle .

# 1-2) common 모듈의 plain JAR만 미리 빌드해 의존성 캐시 활용
RUN chmod +x gradlew \
 && ./gradlew clean :common:jar --no-daemon -x test

# 1-3) 실제 소스 복사 및 auth 모듈 Fat JAR 빌드
COPY common/ common/
COPY auth/ auth/
RUN ./gradlew :auth:bootJar --no-daemon -x test

# 2) Runtime 단계: JRE 기반
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app

# 2-1) auth 모듈에서 생성된 실행 가능한 JAR만 복사
COPY --from=builder /app/auth/build/libs/auth-*.jar newshabit-auth.jar

# 2-2) JVM 옵션과 포트 설정
ENV JAVA_OPTS="-Xms256m -Xmx512m"
EXPOSE 8081

# 2-3) 프로파일 활성화하여 애플리케이션 실행
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/newshabit-auth.jar"]
