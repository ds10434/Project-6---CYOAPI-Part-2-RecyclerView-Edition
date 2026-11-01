package com.example.rawgapi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var gamesRecyclerView: RecyclerView
    private val gamesList = mutableListOf<Game>()
    private lateinit var adapter: GameAdapter

    private val apiKey = "e778174c365b4ab1924f343630ec03f0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gamesRecyclerView = findViewById(R.id.gamesRecyclerView)
        adapter = GameAdapter(gamesList)
        gamesRecyclerView.adapter = adapter
        gamesRecyclerView.layoutManager = LinearLayoutManager(this)

        fetchGames()
    }

    private fun fetchGames() {
        val client = AsyncHttpClient()
        val url = "https://api.rawg.io/api/games?key=$apiKey"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                val resultsArray = json.jsonObject.getJSONArray("results")
                for (i in 0 until resultsArray.length()) {
                    val gameJson = resultsArray.getJSONObject(i)

                    val title = gameJson.optString("name", "Unknown Title")
                    val released = gameJson.optString("released", "N/A")
                    val rating = gameJson.optDouble("rating", 0.0)
                    val imageUrl = gameJson.optString("background_image", "")

                    val game = Game(title, released, rating, imageUrl)
                    gamesList.add(game)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Toast.makeText(this@MainActivity, "Failed to load games", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
