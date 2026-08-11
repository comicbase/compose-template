package com.example.composetemplate.core.model

/** 纯业务模型，不依赖 Android UI、数据库实体或网络 DTO。 */
data class Article(
    val id: String,
    val title: String,
    val summary: String,
    val category: String,
)

