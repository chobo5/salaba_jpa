FROM openjdk:21-jdk

ARG JAR_FILE=app/build/libs/app-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

# 필요한 설정 파일을 컨테이너로 복사
COPY config/jdbc.properties config/jdbc.properties
COPY config/jwt.properties config/jwt.properties

# 애플리케이션 포트 설정
EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]