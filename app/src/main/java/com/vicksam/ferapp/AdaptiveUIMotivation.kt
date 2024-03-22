package com.vicksam.ferapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.TextView
import com.hanks.htextview.typer.TyperTextView
import org.w3c.dom.Text

class AdaptiveUIMotivation : AppCompatActivity() {

    lateinit var text : TyperTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive_uimotivation)

        text = findViewById(R.id.Typer)

        text.animateText("Hello Hi i am kousik");
        text.typerSpeed = 125

        text.charIncrease = 1
    }
}
