package com.example.storyaplikasi.connection

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class ResponseGetStory(
    val error: Boolean,
    val listStory: List<Story>,
    val message: String
)

@Parcelize
data class Story(
    val createdAt: String,
    val description: String,
    val id: String,
    val name: String,
    val photoUrl: String,
    val lat: Double,
    val lon: Double
) : Parcelable