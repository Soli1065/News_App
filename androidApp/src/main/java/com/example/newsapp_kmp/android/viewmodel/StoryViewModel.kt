package com.example.newsapp_kmp.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp_kmp.di.RepositoryProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.newsapp_kmp.model.StoryItem
import com.example.newsapp_kmp.utils.Result

class StoryViewModel : ViewModel() {

    private val repository = RepositoryProvider.storyRepository

    private val _stories = MutableStateFlow<List<StoryItem>>(emptyList())
    val stories: StateFlow<List<StoryItem>> = _stories

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var allStories: List<StoryItem> = emptyList()

    init {
        fetchStories()
    }

    fun fetchStories() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            when (val result = repository.getStories()) {
                is Result.Success -> {
                    allStories = result.data
                    _stories.value = result.data
                }
                is Result.Error -> {
                    _stories.value = emptyList()
                    _error.value = result.message
                }
            }
            _loading.value = false
        }
    }

    fun filterByTitle(query: String) {
        _stories.value = if (query.isBlank()) {
            allStories
        } else {
            repository.filterByTitle(allStories, query)
        }
    }
}