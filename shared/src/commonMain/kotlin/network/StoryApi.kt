package network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import model.ApiResponse
import model.StoryItem
import utils.Result

open class StoryApi {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    open suspend fun fetchStories(): Result<List<StoryItem>> {
        return try {
            val response: HttpResponse = client.get("https://cbcmusic.github.io/assessment-tmp/data/data.json")
            val result: ApiResponse = response.body()

            if (result.status == "success") {
                Result.Success(result.data)
            } else {
                Result.Error("API responded with status: ${result.status}")
            }

        } catch (e: Exception) {
            Result.Error("Failed to fetch stories", e)
        }
    }
}