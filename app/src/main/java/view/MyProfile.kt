package com.example.myapplication2.view

import android.R.attr.bitmap
import android.graphics.BitmapShader
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.R


class MyProfile : AppCompatActivity() {

    private lateinit var name: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Program search all elements which need and add to vars.
        setContentView(R.layout.my_profile)

        //In this part program take info from auth_activity. It's firstname and second name.
        name = findViewById(R.id.name)
        //Program set this firstname and second name in user profile.
        name.text = intent.getStringExtra("name")
    }
}