package com.example.storyaplikasi.di

import android.content.Context
import com.about.app_substoryapp.Connection.ApiConfig
import com.about.app_substoryapp.Helper.Constant
import com.example.storyaplikasi.StoryRepository
import com.example.storyaplikasi.db.StoryDatabase
import com.example.storyaplikasi.helper.PreferencesHelper

object Injection {
    lateinit var sharedpref: PreferencesHelper


    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        sharedpref = PreferencesHelper(context)
        val apiService = ApiConfig().endPoint
        val token = sharedpref.getString(Constant.PREF_TOKEN).toString()
        return StoryRepository(
            database,
            apiService,
            token
        )

    }

}