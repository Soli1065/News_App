package com.example.newsapp_kmp.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.newsapp_kmp.android.viewmodel.StoryViewModel
import com.example.newsapp_kmp.android.utils.formatUnixTimestamp

@Composable
fun StoryListScreen(viewModel: StoryViewModel) {
    val stories by viewModel.stories.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    var filterText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = filterText,
            onValueChange = {
                filterText = it
                viewModel.filterByTitle(it.text)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search by headline") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            loading -> {
                CircularProgressIndicator()
            }
            error != null -> {
                Text("Error: $error")
            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(stories) { story ->
                        StoryItemCard(
                            imageUrl = story.media?.imageUrl.orEmpty(),
                            headline = story.title.orEmpty(),
                            date = formatUnixTimestamp(story.publishedAtUnix)
                        )
                    }
                }
            }
        }
    }
}