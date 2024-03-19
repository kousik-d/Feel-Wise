package com.vicksam.ferapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class AdaptiveUI : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive_ui)

        val emotion = intent.getStringExtra("EMOTION")
        Toast.makeText(this,"Emotion is ${emotion}",Toast.LENGTH_LONG).show()
    }
}