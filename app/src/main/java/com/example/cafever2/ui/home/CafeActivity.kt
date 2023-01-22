package com.example.cafever2.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cafever2.R

class CafeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe)

        val titlename : TextView = findViewById(R.id.title_cafe)
        val image1 : ImageView = findViewById(R.id.ima1)
        val image2 : ImageView = findViewById(R.id.ima2)
        val image3 : ImageView = findViewById(R.id.ima3)
        val image4 : ImageView = findViewById(R.id.ima4)
        val image5 : ImageView = findViewById(R.id.ima5)
        val image6 : ImageView = findViewById(R.id.ima6)
        val image7 : ImageView = findViewById(R.id.ima7)
        val food1 : TextView = findViewById(R.id.food1)
        val food2 : TextView = findViewById(R.id.food2)
        val food3 : TextView = findViewById(R.id.food3)
        val food4 : TextView = findViewById(R.id.food4)
        val price1 : TextView = findViewById(R.id.price1)
        val price2 : TextView = findViewById(R.id.price2)
        val price3 : TextView = findViewById(R.id.price3)
        val price4 : TextView = findViewById(R.id.price4)

        val bundle : Bundle?=intent.extras
        val title = bundle!!.getString("title_cafe")
        val ima1 = bundle!!.getString("ima1")
        val ima2 = bundle!!.getString("ima2")
        val ima3 = bundle!!.getString("ima3")
        val ima4 = bundle!!.getString("ima4")
        val ima5 = bundle!!.getString("ima5")
        val ima6 = bundle!!.getString("ima6")
        val ima7 = bundle!!.getString("ima7")
        val foodname1 = bundle!!.getString("food1")
        val foodname2 = bundle!!.getString("food2")
        val foodname3 = bundle!!.getString("food3")
        val foodname4 = bundle!!.getString("food4")
        val pricee1 = bundle!!.getString("price1")
        val pricee2 = bundle!!.getString("price2")
        val pricee3 = bundle!!.getString("price3")
        val pricee4 = bundle!!.getString("price4")

        titlename.text = title
        Glide.with(this)
            .load(ima1)
            .into(image1)
        Glide.with(this)
            .load(ima2)
            .into(image2)
        Glide.with(this)
            .load(ima3)
            .into(image3)
        Glide.with(this)
            .load(ima4)
            .into(image4)
        Glide.with(this)
            .load(ima5)
            .into(image5)
        Glide.with(this)
            .load(ima6)
            .into(image6)
        Glide.with(this)
            .load(ima7)
            .into(image7)

        food1.text = foodname1
        food2.text = foodname2
        food3.text = foodname3
        food4.text = foodname4
        price1.text = pricee1
        price2.text = pricee2
        price3.text = pricee3
        price4.text = pricee4
    }
}