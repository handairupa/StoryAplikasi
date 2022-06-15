package com.example.storyaplikasi

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.about.app_substoryapp.Connection.ApiConfig
import com.about.app_substoryapp.Helper.Constant
import com.example.storyaplikasi.adapter.LoadingStateAdapter
import com.example.storyaplikasi.adapter.StoryAdapter
import com.example.storyaplikasi.connection.ResponseGetStory
import com.example.storyaplikasi.databinding.ActivityMainBinding
import com.example.storyaplikasi.helper.PreferencesHelper
import com.example.storyaplikasi.maps.MapsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var sharedpref: PreferencesHelper
    private val api by lazy { ApiConfig().endPoint }
    val adapter = StoryAdapter()

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedpref = PreferencesHelper(this)

        binding.btnAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
        getStory()
    }

    override fun onResume() {
        super.onResume()
        adapter.refresh()
    }

    private fun getStory() {
        api.getStories(
            "Bearer " + sharedpref.getString(Constant.PREF_TOKEN)
        ).enqueue(object : Callback<ResponseGetStory> {
            override fun onResponse(
                call: Call<ResponseGetStory>,
                response: Response<ResponseGetStory>
            ) {
                val data = response.body()
                if (response.isSuccessful) {
                    if (data != null) {
                        showStories()
                        Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "data null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "respon gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseGetStory>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showStories() {
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            })
        mainViewModel.story.observe(this, {
            adapter.submitData(lifecycle, it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.maps -> {
                val i = Intent(this, MapsActivity::class.java)
                i.putExtra("api_key", sharedpref.getString(Constant.PREF_TOKEN))
                startActivity(i)
                true
            }
            R.id.logout -> {
                sharedpref.clear()
                finishAffinity()
                true
            }
            else -> true
        }
    }
}

