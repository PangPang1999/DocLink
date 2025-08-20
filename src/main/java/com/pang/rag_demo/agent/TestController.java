package com.pang.rag_demo.agent;

import com.pang.rag_demo.agent.tools.MathTool;
import com.pang.rag_demo.agent.tools.RagSearchTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MathTool mathTool;

    @Autowired
    private RagSearchTool ragSearchTool;

    @GetMapping("/tools")
    public String testTools() {
        StringBuilder result = new StringBuilder();

        // 测试数学工具
        try {
            String mathResult = mathTool.mathCalc("2+3*4");
            result.append("数学工具测试 (2+3*4): ").append(mathResult).append("\n");
        } catch (Exception e) {
            result.append("数学工具测试失败: ").append(e.getMessage()).append("\n");
        }

        // 测试RAG工具
        try {
            String ragResult = ragSearchTool.ragSearch("测试查询", 2);
            if (ragResult.isEmpty()) {
                result.append("RAG工具测试: 无结果\n");
            } else {
                result.append("RAG工具测试: 有结果 (").append(ragResult.length()).append(" 字符)\n");
            }
        } catch (Exception e) {
            result.append("RAG工具测试失败: ").append(e.getMessage()).append("\n");
        }

        return result.toString();
    }
}