#!/bin/bash

echo "=== 智能体系统测试 ==="
echo

echo "1. 测试智能体健康状态..."
curl -s http://localhost:8080/agent/health
echo -e "\n"

echo "2. 测试工具是否正常工作..."
curl -s http://localhost:8080/test/tools
echo -e "\n"  # 确保有换行

echo "3. 测试 RAG 检索工具（模型会自行决定是否用工具）..."
echo "问题：请用 ragSearch 检索 '美联储 降息 影响'，并分两点总结"
curl -X POST http://localhost:8080/agent \
  -H "Content-Type: application/json" \
  -d '{"question":"请用 ragSearch 检索 \"美联储 降息 影响\"，并分两点总结"}'
echo -e "\n"

echo "4. 测试计算工具（显式提示模型使用）..."
echo "问题：请用 mathCalc 计算 3*(2+4)/3"
curl -X POST http://localhost:8080/agent \
  -H "Content-Type: application/json" \
  -d '{"question":"请用 mathCalc 计算 3*(2+4)/3"}'
echo -e "\n"

echo "5. 测试自动工具选择..."
echo "问题：计算 15 + 25 * 2 的结果"
curl -X POST http://localhost:8080/agent \
  -H "Content-Type: application/json" \
  -d '{"question":"计算 15 + 25 * 2 的结果"}'
echo -e "\n"

echo "6. 测试复杂数学表达式..."
echo "问题：计算 (10 + 5) * 2 / 3 - 1"
curl -X POST http://localhost:8080/agent \
  -H "Content-Type: application/json" \
  -d '{"question":"计算 (10 + 5) * 2 / 3 - 1"}'
echo -e "\n"

echo "=== 测试完成 ==="
