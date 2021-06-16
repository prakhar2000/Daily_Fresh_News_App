package com.example.dailyfresh

import android.R
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.newsitem.*
import kotlinx.android.synthetic.main.newsitem.view.*


class MainActivity : AppCompatActivity(), Newsitemclick {

    private lateinit var madapter: Newslistadapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.dailyfresh.R.layout.activity_main)

        recyclerview.layoutManager=LinearLayoutManager(this)
        fetchdata()
        madapter=Newslistadapter(this)
        recyclerview.adapter=madapter

    }

    private fun fetchdata(){

        val url="https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"




        val jsonObject=JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsjsonarray = it.getJSONArray("articles")
                val newsarray = ArrayList<News>()
                for (i in 0 until newsjsonarray.length()) {
                    val newsjsonobject = newsjsonarray.getJSONObject(i)
                    val news = News(
                        newsjsonobject.getString("title"),
                        newsjsonobject.getString("author"),
                        newsjsonobject.getString("url"),
                        newsjsonobject.getString("urlToImage")
                    )
                    newsarray.add(news)
//                    for(i in 0 until newsarray.size){
//                        println(newsarray[i].imageurl)
//                    }

                }
                madapter.updatenews(newsarray)
            },
            Response.ErrorListener {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObject)
    }

    override fun onitemclick(item: News) {



        val builder= CustomTabsIntent.Builder()
        val colorInt: Int = Color.parseColor("#ffffff")

        builder.setToolbarColor(colorInt)

        builder.addDefaultShareMenuItem()
        val bitmap = BitmapFactory.decodeResource(resources, com.example.dailyfresh.R.drawable.ic_action_name)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, item.url)

        val requestCode = 100

        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        builder.setActionButton(bitmap, "Share Link", pendingIntent, true)
        val customTabsIntent = builder.build()


        customTabsIntent.launchUrl(this, Uri.parse(item.url))


    }
}

