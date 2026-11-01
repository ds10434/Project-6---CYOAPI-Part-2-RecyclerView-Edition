package com.example.rawgapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameImageView: ImageView = itemView.findViewById(R.id.gameImageView)
        val gameTitleTextView: TextView = itemView.findViewById(R.id.gameTitleTextView)
        val gameReleaseTextView: TextView = itemView.findViewById(R.id.gameReleaseTextView)
        val gameRatingTextView: TextView = itemView.findViewById(R.id.gameRatingTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]

        holder.gameTitleTextView.text = game.title
        holder.gameReleaseTextView.text = "Release Date: ${game.released}"
        holder.gameRatingTextView.text = "Rating: ${game.rating} / 5"

        Glide.with(holder.itemView.context)
            .load(game.imageUrl)
            .into(holder.gameImageView)
    }

    override fun getItemCount(): Int {
        return games.size
    }
}
