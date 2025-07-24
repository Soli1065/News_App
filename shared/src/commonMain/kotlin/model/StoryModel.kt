package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val status: String,
    val data: List<StoryItem>
)

@Serializable
data class StoryItem(
    val id: Long,
    val title: String,
    val description: String,
    val author: String,
    val source: String,
    val isLocal: Boolean,
    val publishedAtUnix: Long,
    val version: String,
    val media: Media
)

@Serializable
data class Media(
    val imageUrl: String
)