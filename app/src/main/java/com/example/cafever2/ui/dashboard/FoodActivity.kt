package com.example.cafever2.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cafever2.R

class FoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        val titlename : TextView = findViewById(R.id.title_food)
        val image1 : ImageView = findViewById(R.id.imaf1)
        val image11 : ImageView = findViewById(R.id.imaf2)
        val image2 : ImageView = findViewById(R.id.imaf3)
        val image3 : ImageView = findViewById(R.id.imaf4)
        val cafename : TextView = findViewById(R.id.cafename)
        val price : TextView = findViewById(R.id.price)

        val bundle : Bundle?=intent.extras
        val title = bundle!!.getString("title_food")
        val ima1 = bundle!!.getString("imaf1")
        val ima11 = bundle!!.getString("imaf2")
        val ima2 = bundle!!.getString("imaf3")
        val ima3 = bundle!!.getString("imaf4")
        val cafe = bundle!!.getString("cafename")
        val pricee = bundle!!.getString("price")


        titlename.text = title
        Glide.with(this)
            .load(ima1)
            .into(image1)
        Glide.with(this)
            .load(ima11)
            .into(image11)
        Glide.with(this)
            .load(ima2)
            .into(image2)
        Glide.with(this)
            .load(ima3)
            .into(image3)
        cafename.text = cafe
        price.text = pricee

    }
}