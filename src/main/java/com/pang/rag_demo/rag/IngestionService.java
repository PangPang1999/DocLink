package com.pang.rag_demo.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
public class IngestionService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(IngestionService.class);
    private final VectorStore vectorStore;

    @Value("classpath:/docs/article.pdf")
    private Resource resource;

    public IngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1) 用 Tika 读取文档（支持 txt / doc / docx / pptx / html / pdf 等多种格式）
        DocumentReader reader = new TikaDocumentReader(resource);

        // 2) 切分为 chunk（可根据需要调参：chunkSize, overlap…）
        TextSplitter splitter = new TokenTextSplitter();
        // 例如自定义：
        // TextSplitter splitter = new TokenTextSplitter(800, 200, 4);

        // 3) 写入向量库
        vectorStore.accept(splitter.apply(reader.get()));

        log.info("VectorStore loaded with data from {}!", resource.getFilename());
    }
}