package com.example.newsapp_kmp.cache

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import com.example.newsapp_kmp.model.StoryItem

private val jsonParser = Json {
    ignoreUnknownKeys = true
}

open class CacheManager(private val settings: Settings = Settings()) {

    private val cacheKey = "cached_stories"

    open fun saveStories(stories: List<StoryItem>) {
        val json = jsonParser.encodeToString(stories)
        settings[cacheKey] = json
    }

    open fun loadStories(): List<StoryItem> {
        val cachedJson = settings.getStringOrNull(cacheKey)
        if (cachedJson == null) return emptyList()

        return try {
            jsonParser.decodeFromString<List<StoryItem>>(cachedJson)
        } catch (e: Exception) {
            emptyList()
        }
    }
}