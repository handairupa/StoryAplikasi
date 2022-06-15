package com.about.app_substoryapp.Connection

import com.example.storyaplikasi.connection.ResponseAddStory
import com.example.storyaplikasi.connection.ResponseGetStory
import com.example.storyaplikasi.connection.ResponseStory
import com.example.storyaplikasi.helper.PreferencesHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterfaces {

    var sharedpref: PreferencesHelper


    @FormUrlEncoded
    @POST("register")
    fun Create_user(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseRegister>


    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Call<ResponseAddStory>

    @GET("stories?location=1")
    fun getStories(
        @Header("Authorization")
        token: String

    ): Call<ResponseGetStory>

    @GET("stories")
    suspend fun getStoriesPagingg(
        @Header("Authorization")
        auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ResponseStory
}