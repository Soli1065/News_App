package com.example.newsapp_kmp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp_kmp.android.ui.NewsAppTheme
import com.example.newsapp_kmp.android.ui.StoryListScreen
import com.example.newsapp_kmp.android.viewmodel.StoryViewModel
import com.example.newsapp_kmp.di.RepositoryProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RepositoryProvider.init(applicationContext)

        setContent {
            NewsAppTheme {
                val viewModel: StoryViewModel = viewModel()
                StoryListScreen(viewModel)
            }
        }
    }
}



//package com.example.newsapp_kmp.android
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.newsapp_kmp.Greeting
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MyApplicationTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    GreetingView(Greeting().greet())
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun GreetingView(text: String) {
//    Text(text = text)
//}
//
//@Preview
//@Composable
//fun DefaultPreview() {
//    MyApplicationTheme {
//        GreetingView("Hello, Android!")
//    }
//}

