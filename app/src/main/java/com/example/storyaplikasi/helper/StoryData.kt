package com.example.storyaplikasi.helper

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class StoryData(
    val name: String?,
    val deskripsi: String?,
    val imageurl: String?
) : Parcelable