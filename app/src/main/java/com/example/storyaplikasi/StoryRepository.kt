package com.example.storyaplikasi

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.about.app_substoryapp.Connection.ApiInterfaces
import com.example.storyaplikasi.connection.StoryResponseItem
import com.example.storyaplikasi.db.StoryDatabase

class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiInterfaces, private val token: String) {
    fun getStories(): LiveData<PagingData<StoryResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).liveData
    }
}