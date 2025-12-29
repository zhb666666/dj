# like-admin 后台管理Dockerfile
FROM registry.cn-hangzhou.aliyuncs.com/zhbpublic/openjdk:11-8.6

# 设置时区（可选）
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

# 复制jar包到容器（使用时需要把like-admin.jar放在同目录）
COPY target/like-front-1.0.0.jar /app.jar

# 暴露端口
EXPOSE 8088

# 启动命令
ENTRYPOINT ["java", "-jar", "/app.jar"]
CMD ["--spring.profiles.active=prod"]