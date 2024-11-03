package com.mita.login

data class ReelsItem(
    val id: String,
    val videoUrl: String,
    val title: String,
    val username: String,
    val description: String,
    val likesCount: Int,
    val commentsCount: Int,
    val shares: Int
)
