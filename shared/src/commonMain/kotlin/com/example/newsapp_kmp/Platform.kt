package com.example.newsapp_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform