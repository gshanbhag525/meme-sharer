package com.gshan.memeshare

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val shareButton: Button = findViewById(R.id.shareButton)
        val nextButton: Button = findViewById(R.id.nextButton)

        loadMeme()
    }

    private fun loadMeme () {
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        val memeImageView: ImageView = findViewById(R.id.memeImageView)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val url = response.getString("url")
                Glide.with(this).asBitmap().load(url).listener(object: RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                }).into(memeImageView)
                },
            Response.ErrorListener{
                Toast.makeText(this, "Something went wrong!" , Toast.LENGTH_LONG).show()
            })

        // Add the request to the Reques    tQueue.
        queue.add(jsonObjectRequest)
    }

    fun shareMeme(view: View) {

    }

    fun nextMeme(view: View) {
        loadMeme()
    }

    override fun onResume() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        super.onResume()

    }
}