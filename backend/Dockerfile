# 使用 OpenJDK 作为基础镜像
FROM openjdk:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制构建的 JAR 文件到容器中
COPY target/ai-finance-1.0-SNAPSHOT.jar app.jar

# 暴露 8080 端口
EXPOSE 8080

# 运行应用
ENTRYPOINT ["java", "-jar", "app.jar"]
