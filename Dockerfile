# 베이스 이미지 설정 (Java 17)
FROM openjdk:17

# 애플리케이션 JAR 파일을 컨테이너에 복사
COPY build/libs/hhplus_week3_4_5-0.0.1-SNAPSHOT.jar /app/hhplus_week3_4_5.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/hhplus_week3_4_5.jar"]