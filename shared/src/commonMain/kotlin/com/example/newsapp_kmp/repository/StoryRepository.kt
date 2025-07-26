package com.example.newsapp_kmp.repository

import com.example.newsapp_kmp.cache.CacheManager
import com.example.newsapp_kmp.model.StoryItem
import com.example.newsapp_kmp.network.StoryApi
import com.example.newsapp_kmp.utils.Result

class StoryRepository(
    private val api: StoryApi = StoryApi(),
    private val cache: CacheManager = CacheManager()
) {

    suspend fun getStories(): Result<List<StoryItem>> {
        val result = api.fetchStories()
        return when (result) {
            is Result.Success -> {
                cache.saveStories(result.data)
                Result.Success(result.data)
            }
            is Result.Error -> {
                val cached = cache.loadStories()
                if (cached.isNotEmpty()) {
                    Result.Success(cached)
                } else {
                    result
                }
            }
        }
    }

    fun filterByTitle(stories: List<StoryItem>, query: String): List<StoryItem> {
        if (query.isBlank()) return stories
        return stories.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }
}