# 1) Builder 단계: JDK 기반
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# 1-1) Gradle 래퍼 및 루트 빌드 스크립트 복사
COPY gradlew .
COPY gradle/ gradle/
COPY settings.gradle .
COPY build.gradle .

# 1-2) 의존성 캐시를 위해 plain JAR만 먼저 빌드
RUN chmod +x gradlew \
 && ./gradlew clean \
    :aiprocess:jar \
    :auth:jar  \
    :common:jar  \
    :common-auth:jar  \
    :common-event:jar  \
    :crawl:jar  \
    :news:jar  \
    :user:jar  \
    --no-daemon -x test

COPY aiprocess/ aiprocess/
COPY auth/ auth/
COPY common/ common/
COPY common-auth/ common-auth/
COPY common-event/ common-event/
COPY crawl/ crawl/
COPY news/ news/
COPY newshabit/ newshabit/
COPY user/ user/

RUN ./gradlew :newshabit:bootJar --no-daemon -x test

FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app

COPY --from=builder /app/user/build/libs/newshabit-*.jar newshabit.jar

EXPOSE 8080

# 2-3) 셸 형식 ENTRYPOINT로 $JAVA_OPTS 확장 보장
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/newshabit.jar"]
