# 1) Builder 단계: JDK 기반
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Gradle 래퍼 및 빌드 스크립트 복사
COPY gradlew .
COPY gradle/ gradle/
COPY settings.gradle .
COPY build.gradle .

# 소스 전체 복사 (src 하위에 모든 코드가 있으므로)
COPY src/ src/

RUN chmod +x gradlew \
 && ./gradlew clean bootJar --no-daemon -x test

FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
