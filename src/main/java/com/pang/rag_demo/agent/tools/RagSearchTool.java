package com.pang.rag_demo.agent.tools;

import org.springframework.ai.document.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RagSearchTool {

    private final VectorStore vectorStore;

    public RagSearchTool(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Tool(description = "从向量库检索与 query 最相关的片段，返回拼接文本")
    public String ragSearch(String query, Integer topK) {
        if (!StringUtils.hasText(query)) return "";

        int k = (topK == null || topK <= 0 || topK > 10) ? 3 : topK;

        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(k)
                .build();

        List<Document> docs = vectorStore.similaritySearch(request);

        return docs.stream()
                .map(d -> d.getText() != null ? d.getText() : d.getFormattedContent())
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n---\n"));
    }
}
