# Base Image
FROM eclipse-temurin:21-jdk

# Directory
WORKDIR /app

# 빌드 경로
ARG JAR_PATH=build/libs

# 빌드 파일 복사
COPY ${JAR_PATH}/*.jar hanjari.jar

# 실행
ENTRYPOINT ["java", "-jar", "hanjari.jar"]
