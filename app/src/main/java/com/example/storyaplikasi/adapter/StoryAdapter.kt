package com.example.storyaplikasi.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyaplikasi.DetailActivity
import com.example.storyaplikasi.R
import com.example.storyaplikasi.connection.StoryResponseItem

class StoryAdapter :
    PagingDataAdapter<StoryResponseItem, StoryAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgstory: ImageView = itemView.findViewById(R.id.img_story)
        val user_name: TextView = itemView.findViewById(R.id.Username_)
        val descript: TextView = itemView.findViewById(R.id.Desc_)


        fun bind(story: StoryResponseItem) {
            Glide.with(itemView.context)
                .load(story.photoUrl)
                .into(imgstory)

            user_name.text = story.name
            descript.text = story.description


            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("story", story)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(imgstory, "img"),
                        Pair(user_name, "user"),
                        Pair(descript, "deskripsi")
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_story, parent, false)
        return ListViewHolder(view)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryResponseItem>() {
            override fun areItemsTheSame(
                oldItem: StoryResponseItem,
                newItem: StoryResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StoryResponseItem,
                newItem: StoryResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }
}
