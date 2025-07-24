package repository

import cache.CacheManager
import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.test.runTest
import model.StoryItem
import network.StoryApi
import utils.Result
import kotlin.test.*


class StoryRepositoryTest {

    private val sampleStories = listOf(
        StoryItem(1, "Hello World", "Desc", "Author", "Source", true, 0L, "1.0", media = model.Media("img")),
        StoryItem(2, "Kotlin is great", "Desc", "Author", "Source", true, 0L, "1.0", media = model.Media("img"))
    )

    @Test
    fun test_getStories_success() = runTest {
        val fakeApi = object : StoryApi() {
            override suspend fun fetchStories(): Result<List<StoryItem>> {
                return Result.Success(sampleStories)
            }
        }

        val fakeCache = CacheManager(MapSettings())

        val repo = StoryRepository(fakeApi, fakeCache)
        val result = repo.getStories()

        assertTrue(result is Result.Success)
        assertEquals(2, (result as Result.Success).data.size)
    }

    @Test
    fun test_getStories_fallback_to_cache() = runTest {
        val fakeApi = object : StoryApi() {
            override suspend fun fetchStories(): Result<List<StoryItem>> {
                return Result.Error("Network failure")
            }
        }

        val fakeCache = CacheManager(MapSettings()).apply {
            saveStories(sampleStories)
        }

        val repo = StoryRepository(fakeApi, fakeCache)
        val result = repo.getStories()

        assertTrue(result is Result.Success)
        assertEquals(2, (result as Result.Success).data.size)
    }

    @Test
    fun test_getStories_no_cache_and_error() = runTest {
        val fakeApi = object : StoryApi() {
            override suspend fun fetchStories(): Result<List<StoryItem>> {
                return Result.Error("Network failure")
            }
        }

        val fakeCache = CacheManager(MapSettings())

        val repo = StoryRepository(fakeApi, fakeCache)
        val result = repo.getStories()

        assertTrue(result is Result.Error)
    }

    @Test
    fun test_filterByTitle_returns_matches() {
        val dummyApi = object : StoryApi() {}
        val dummyCache = CacheManager(MapSettings())
        val repo = StoryRepository(dummyApi, dummyCache)

        val filtered = repo.filterByTitle(sampleStories, "kotlin")
        assertEquals(1, filtered.size)
        assertEquals("Kotlin is great", filtered[0].title)
    }

    @Test
    fun test_filterByTitle_blank_returns_all() {
        val dummyApi = object : StoryApi() {}
        val dummyCache = CacheManager(MapSettings())
        val repo = StoryRepository(dummyApi, dummyCache)

        val filtered = repo.filterByTitle(sampleStories, "")
        assertEquals(2, filtered.size)
    }
}