# Java 21 JRE 기반, 경량 Alpine
FROM eclipse-temurin:21-jre-alpine

# 필수 툴 (ca-certificates) 설치 (Alpine은 기본 미포함)
RUN apk add --no-cache curl ca-certificates openjdk17 keytool


# 인증서 복사
COPY http_ca.crt /usr/local/share/ca-certificates/http_ca.crt

# 인증서 등록
RUN update-ca-certificates

# JVM truststore에 인증서 수동 등록
RUN keytool -importcert -trustcacerts -alias elasticsearch-ca \
    -file /usr/local/share/ca-certificates/http_ca.crt \
    -keystore ${JAVA_HOME}/lib/security/cacerts \
    -storepass changeit -noprompt

# 작업 디렉토리
WORKDIR /app

# 빌드된 JAR 복사
COPY build/libs/app.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-Xms512m", "-Xmx768m", "-jar", "/app/app.jar"]