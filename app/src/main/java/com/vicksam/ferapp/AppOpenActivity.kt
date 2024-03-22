package com.vicksam.ferapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.vicksam.ferapp.R

class AppOpenActivity : AppCompatActivity() {
    lateinit var textButton : AppCompatButton
    lateinit var videoButton: AppCompatButton
    lateinit var breathText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_open)

        textButton = findViewById(R.id.TextMeButton);
        videoButton = findViewById(R.id.VideoBtn);

        videoButton.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        textButton.setOnClickListener {
            startActivity(Intent(this,TextRecognition::class.java))
        }
    }
}