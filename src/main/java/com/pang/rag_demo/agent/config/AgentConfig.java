package com.pang.rag_demo.agent.config;

import com.pang.rag_demo.agent.tools.MathTool;
import com.pang.rag_demo.agent.tools.RagSearchTool;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    @Bean
    public PromptTemplate agentPromptTemplate() {
        return new PromptTemplate("""
            你是一个智能助手，能够使用各种工具来帮助用户解决问题。
            
            可用的工具有：
            1. mathCalc - 用于计算数学表达式
            2. ragSearch - 用于检索相关文档信息
            
            请根据用户的问题，选择合适的工具来完成任务。如果用户明确要求使用某个工具，请按照要求执行。
            如果用户没有明确指定工具，请根据问题的性质自动选择合适的工具。
            
            用户问题：{question}
            
            请回答用户的问题，并在需要时使用相应的工具。
            """);
    }
}
