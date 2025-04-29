# 1) Builder 단계: JDK 기반
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# 1-1) Gradle 래퍼 및 루트 빌드 스크립트 복사
COPY gradlew .
COPY gradle/ gradle/
COPY settings.gradle .
COPY build.gradle .

# 1-2) 의존성 캐시를 위해 common, common-event plain JAR만 먼저 빌드
RUN chmod +x gradlew \
 && ./gradlew clean :common:jar :common-event:jar --no-daemon -x test

COPY common/ common/
COPY common-event/ common-event/
COPY aiprocess/ aiprocess/

RUN ./gradlew :aiprocess:bootJar --no-daemon -x test

FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app

COPY --from=builder /app/aiprocess/build/libs/aiprocess-*.jar newshabit-aiprocess.jar

ENV JAVA_OPTS="-Xms128m -Xmx512m -XX:+UseG1GC"
EXPOSE 8080

# 2-3) 셸 형식 ENTRYPOINT로 $JAVA_OPTS 확장 보장
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/newshabit-aiprocess.jar"]
