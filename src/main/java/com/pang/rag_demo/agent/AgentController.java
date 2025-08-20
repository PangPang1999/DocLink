package com.pang.rag_demo.agent;

import com.pang.rag_demo.agent.config.AgentConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final ChatClient chatClient;
    private final PromptTemplate agentPromptTemplate;

    @Autowired
    public AgentController(ChatClient.Builder builder, PromptTemplate agentPromptTemplate) {
        this.chatClient = builder.build();
        this.agentPromptTemplate = agentPromptTemplate;
    }

    @PostMapping
    public String processQuestion(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        if (question == null || question.trim().isEmpty()) {
            return "请提供问题内容";
        }

        try {
            // 构建提示
            String promptText = agentPromptTemplate.render(Map.of("question", question));
            
            // 调用模型并获取响应
            // Spring AI会自动发现带有@Tool注解的工具
            String response = chatClient.prompt()
                    .user(promptText)
                    .call()
                    .content();
            
            return response;
            
        } catch (Exception e) {
            return "处理问题时发生错误: " + e.getMessage();
        }
    }

    @GetMapping("/health")
    public String health() {
        return "Agent service is running";
    }
}
