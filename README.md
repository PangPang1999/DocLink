# RAG-Agent (Spring AI Minimal Implementation)

> **技术栈**：Java 17+ · Spring Boot · Spring AI · PostgreSQL + PGvector · Ollama
> **说明**：这是一个 **最小实现 (Minimal Implementation)** 的 **RAG + Agent** 系统，用于演示如何在本地环境中实现基于文档检索的智能问答与工具调用。
> **特别提示**：文档中保留了我的踩坑记录，方便理解为什么最终选择当前方案。

---

## 📑 目录

* [背景](#背景)
* [功能特性](#功能特性)
* [环境依赖](#环境依赖)
* [安装与配置](#安装与配置)

  * [1. 数据库 (PGvector)](#1-数据库-pgvector)
  * [2. Spring Boot 配置](#2-spring-boot-配置)
  * [3. Ollama 模型](#3-ollama-模型)
* [系统结构](#系统结构)

  * [Agent 模块](#agent-模块)
  * [RAG 模块](#rag-模块)
  * [工具模块](#工具模块)
* [运行与测试](#运行与测试)
* [踩坑记录](#踩坑记录)
* [总结与后续计划](#总结与后续计划)

---

## 背景

在构建 AI 应用时，RAG（Retrieval-Augmented Generation）和 Agent（具备工具调用与记忆的自主执行体）是最核心的两大技术模式：

1. **RAG**：将文档向量化存储 → 检索相关内容 → 提供上下文增强大模型回答。
2. **Agent**：在 RAG 之上，进一步支持调用工具（例如计算器、搜索模块），并维护对话记忆。

本项目基于 **Spring AI + Ollama + PostgreSQL + PGvector** 搭建，支持在本地运行，避免依赖 OpenAI/Google Cloud 等海外付费 API。

---

## 功能特性

* [x] **RAG**：支持 PDF/Word/TXT/HTML 文档解析、切分、向量化与检索
* [x] **Agent**：支持工具调用与对话记忆
* [x] **工具集成**：内置数学计算与 RAG 搜索工具
* [x] **本地大模型**：基于 Ollama 运行 Embedding + Chat 模型
* [x] **REST API 接口**：提供问答、检索、Agent 调用 API
* [x] **最小可运行 Demo**：开箱即用，便于理解核心原理

---

## 环境依赖

* **Java** 17+
* **Maven** 3.8+
* **Docker & Docker Compose**
* **PostgreSQL with PGvector**
* **Ollama** (需下载模型)

---

## 安装与配置

### 1. 数据库 (PGvector)

使用 Docker Compose 启动 PostgreSQL + PGvector 扩展。

### 2. Spring Boot 配置

在 `application.yml` 中配置数据库连接、模型服务地址、向量存储表结构。

### 3. Ollama 模型

安装 Ollama 并下载所需模型（如 `nomic-embed-text:v1.5` 和 `qwen3:8b`）。

---

## 系统结构

### Agent 模块

* 提供对话接口
* 自动发现并调用工具
* 支持对话上下文记忆

### RAG 模块

* 文档解析与切分
* 向量化存储
* 相似度检索与上下文增强

### 工具模块

* **MathTool**：数学表达式计算
* **RagSearchTool**：文档检索

---

## 运行与测试

1. 启动数据库（Docker Compose）
2. 启动 Ollama 服务并加载模型
3. 启动 Spring Boot 应用
4. 使用 curl/HTTPie/Postman 测试接口

---

## 踩坑记录

### OpenAI API
- **问题**: 需要 API Key，国内无法购买
- **尝试**: 花费时间尝试购买，但无法获得结果
- **结果**: ❌ 放弃

### DeepSeek
- **问题**: 仅提供 Chat 模型，缺少 Embedding 模型适配
- **参考**: [Spring AI DeepSeek 文档](https://docs.spring.io/spring-ai/reference/api/chat/deepseek-chat.html)
- **结果**: ❌ 无法满足 RAG 需求

### Google Vertex AI
- **问题**: 需要引入 Vertex AI Embeddings + Vertex AI Gemini
- **限制**: 国内付款受限
- **结果**: ❌ 无法使用

### Ollama ✅ (最终方案)
- **官网**: https://ollama.com
- **优势**: 
  - 本地运行，无需付费
  - 支持 Embedding + Chat 模型
  - 动态加载，资源可控
- **模型推荐** (M2 Max 32G 配置):
  - **Embedding**: `nomic-embed-text:v1.5` - 优秀的文本向量化模型
  - **Chat**: `qwen3:8b` - 中文支持好，资源占用适中
- **特性**: 按需加载模型，自动释放内存资源
  