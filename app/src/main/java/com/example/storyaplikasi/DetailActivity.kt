package com.example.storyaplikasi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyaplikasi.connection.StoryResponseItem
import com.example.storyaplikasi.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setdata()

    }

    private fun setdata() {
        val dataStory = intent.getParcelableExtra<StoryResponseItem>("story") as StoryResponseItem

        Glide.with(this)
            .load(dataStory.photoUrl)
            .into(binding.imagestory)
        binding.descStory.text = dataStory.description
        binding.nameUser.text = dataStory.name
    }
}