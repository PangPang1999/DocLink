#!/bin/bash

echo "=== 快速功能测试 ==="
echo

# 等待应用启动
echo "等待应用启动..."
sleep 5

# 测试健康状态
echo "测试健康状态..."
if curl -s http://localhost:8080/agent/health > /dev/null; then
    echo "✓ 智能体服务正常"
else
    echo "✗ 智能体服务异常"
    exit 1
fi

# 测试工具
echo "测试工具..."
if curl -s http://localhost:8080/test/tools > /dev/null; then
    echo "✓ 工具服务正常"
else
    echo "✗ 工具服务异常"
    exit 1
fi

echo
echo "✓ 所有基础服务正常，可以运行完整测试："
echo "  ./test_agent.sh"

