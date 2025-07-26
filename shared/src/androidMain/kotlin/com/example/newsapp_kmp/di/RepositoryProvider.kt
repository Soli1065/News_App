package com.example.newsapp_kmp.di

import android.content.Context
import com.example.newsapp_kmp.cache.CacheManager
import com.example.newsapp_kmp.network.StoryApi
import com.example.newsapp_kmp.repository.StoryRepository
import com.russhwolf.settings.SharedPreferencesSettings

object RepositoryProvider {
    private var initialized = false
    private lateinit var _storyRepository: StoryRepository

    fun init(context: Context) {
        if (!initialized) {
            val sharedPrefs = context.getSharedPreferences("newsapp_prefs", Context.MODE_PRIVATE)
            val settings = SharedPreferencesSettings(sharedPrefs)

            _storyRepository = StoryRepository(
                api = StoryApi(),
                cache = CacheManager(settings)
            )
            initialized = true
        }
    }

    val storyRepository: StoryRepository
        get() {
            if (!initialized) {
                throw IllegalStateException("RepositoryProvider.init(context) must be called before accessing storyRepository")
            }
            return _storyRepository
        }
}