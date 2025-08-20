#!/bin/bash

echo "=== RAG Demo 智能体系统启动脚本 ==="
echo

# 检查Java版本
echo "检查Java版本..."
java -version
echo

# 检查Maven
echo "检查Maven..."
./mvnw --version
echo

# 清理并编译
echo "清理并编译项目..."
./mvnw clean compile
echo

# 启动应用
echo "启动Spring Boot应用..."
echo "应用将在 http://localhost:8080 启动"
echo "按 Ctrl+C 停止应用"
echo

./mvnw spring-boot:run

