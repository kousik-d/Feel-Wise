package com.vicksam.ferapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.TextView
import com.hanks.htextview.typer.TyperTextView
import org.w3c.dom.Text
import kotlin.random.Random
import kotlin.random.nextInt

class AdaptiveUIMotivation : AppCompatActivity() {

    lateinit var text : TyperTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive_uimotivation)

        text = findViewById(R.id.Typer)
        val motivationalQuotes = resources.getStringArray(R.array.motivational_quotes)

        val sizeOfMotivaionalQuotes = motivationalQuotes.size
        val randomIndex = Random.nextInt(sizeOfMotivaionalQuotes-1)
        text.animateText(motivationalQuotes[randomIndex]);
        text.typerSpeed = 140
        text.charIncrease = 1
    }
}
