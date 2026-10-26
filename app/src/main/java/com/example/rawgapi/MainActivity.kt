package com.example.rawgapi

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gameImageView: ImageView
    private lateinit var gameTitleTextView: TextView
    private lateinit var gameReleaseTextView: TextView
    private lateinit var gameRatingTextView: TextView
    private lateinit var fetchGameButton: Button

    private val apiKey = "e778174c365b4ab1924f343630ec03f0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameImageView = findViewById(R.id.gameImageView)
        gameTitleTextView = findViewById(R.id.gameTitleTextView)
        gameReleaseTextView = findViewById(R.id.gameReleaseTextView)
        gameRatingTextView = findViewById(R.id.gameRatingTextView)
        fetchGameButton = findViewById(R.id.fetchGameButton)

        fetchGameButton.setOnClickListener {
            fetchRandomGame()
        }

        fetchRandomGame()
    }

    private fun fetchRandomGame() {
        val client = AsyncHttpClient()
        val randomPage = Random.nextInt(1, 31)
        val url = "https://api.rawg.io/api/games?key=$apiKey&page=$randomPage"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                val resultsArray = json.jsonObject.getJSONArray("results")
                if (resultsArray.length() > 0) {
                    val randomIndex = Random.nextInt(resultsArray.length())
                    val game = resultsArray.getJSONObject(randomIndex)

                    val title = game.getString("name")
                    val released = game.getString("released")
                    val rating = game.getDouble("rating")
                    val imageUrl = game.getString("background_image")

                    gameTitleTextView.text = title
                    gameReleaseTextView.text = "Release Date: $released"
                    gameRatingTextView.text = "Rating: $rating / 5"

                    Glide.with(this@MainActivity)
                        .load(imageUrl)
                        .into(gameImageView)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
            }
        })
    }
}
