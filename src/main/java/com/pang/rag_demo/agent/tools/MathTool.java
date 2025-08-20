package com.pang.rag_demo.agent.tools;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class MathTool {

    @Tool(description = "计算简单数学表达式（支持 0-9、+ - * / ( ) 和空格）")
    public String mathCalc(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            return "错误：表达式不能为空";
        }

        String cleanExpression = expression.trim();
        if (!cleanExpression.matches("[0-9+\\-*/().\\s]+")) {
            return "错误：表达式包含非法字符。只允许数字、+ - * / ( ) 和空格。";
        }

        try {
            // 使用exp4j计算表达式
            Expression e = new ExpressionBuilder(cleanExpression).build();
            double result = e.evaluate();

            // 格式化结果
            if (result == (long) result) {
                return String.valueOf((long) result);
            } else {
                return String.format("%.2f", result);
            }

        } catch (Exception ex) {
            return "计算错误: " + ex.getMessage();
        }
    }
}