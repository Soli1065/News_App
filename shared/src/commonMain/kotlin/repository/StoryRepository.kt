package repository

import model.StoryItem
import network.StoryApi
import utils.Result

class StoryRepository(
    private val api: StoryApi = StoryApi()
) {

    suspend fun getStories(): Result<List<StoryItem>> {
        return api.fetchStories()
    }

    fun filterByTitle(stories: List<StoryItem>, query: String): List<StoryItem> {
        if (query.isBlank()) return stories
        return stories.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }
}