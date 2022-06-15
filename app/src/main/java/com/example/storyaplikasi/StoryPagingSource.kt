package com.example.storyaplikasi

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.about.app_substoryapp.Connection.ApiInterfaces
import com.example.storyaplikasi.connection.StoryResponseItem

class StoryPagingSource(private val apiService: ApiInterfaces, private val token: String) :
    PagingSource<Int, StoryResponseItem>() {
    override fun getRefreshKey(state: PagingState<Int, StoryResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryResponseItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData =
                apiService.getStoriesPagingg("Bearer $token", position, params.loadSize)
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}