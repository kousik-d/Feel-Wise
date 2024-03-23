package com.vicksam.ferapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.github.florent37.viewanimator.AnimationListener
import com.github.florent37.viewanimator.ViewAnimator

class AdaptiveUI : AppCompatActivity() {
    lateinit var playMusicBtn : AppCompatButton
    lateinit var flowerImage : ImageView
    lateinit var breadthText : TextView
    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive_ui)
        playMusicBtn = findViewById(R.id.playMusicBtn)
        flowerImage = findViewById(R.id.flowerImage)
        breadthText = findViewById(R.id.BreathText)

        startIntroAnimation()

//        com.github.florent37.viewanimator.ViewAnimator.animate(flowerImage)
//            .alpha(0.1f)
//            .dp().translationY(-1000f)
//            .start()

        mediaPlayer = MediaPlayer.create(this,R.raw.pleasent)

        playMusicBtn.setOnClickListener {
            startAnimation()
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
            }else{
                mediaPlayer.pause()
            }
        }
        //val emotion = intent.getStringExtra("EMOTION")
        //Toast.makeText(this,"Emotion is ${emotion}",Toast.LENGTH_LONG).show()
    }

    fun startIntroAnimation(){
        ViewAnimator
            .animate(breadthText)
            .scale(*floatArrayOf(0f,1f))
            .duration(1500)
            .onStart { breadthText.text = "Breathe"; }
            .start()
    }

     fun startAnimation(){
         ViewAnimator
             .animate(flowerImage)
             .alpha(*floatArrayOf(0f,1f))
             .onStart { breadthText.text = "Inhale... Exhale"; }
             .decelerate()
             .duration(1000)
             .thenAnimate(flowerImage)
             .scale(*floatArrayOf(0.02f,1.5f,0.02f))
             .rotation(*floatArrayOf(360f))
             .repeatCount(6)
             .accelerate()
             .duration(5000)
             .onStop { breadthText.text = "Good Job"
                flowerImage.scaleX = 1.0f
                flowerImage.scaleY = 1.0f
             }
             .start()
     }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer.pause()
    }
}