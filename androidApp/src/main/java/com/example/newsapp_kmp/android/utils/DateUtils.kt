package com.example.newsapp_kmp.android.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatUnixTimestamp(unix: Long): String {
    val date = Date(unix * 1000)
    val format = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    return format.format(date)
}