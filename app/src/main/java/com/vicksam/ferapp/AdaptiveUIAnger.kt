package com.vicksam.ferapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.skyfishjy.library.RippleBackground
import kotlinx.android.synthetic.main.activity_adaptive_uianger.SelectTime
import kotlinx.android.synthetic.main.activity_adaptive_uianger.view.ripple_animation
import nl.joery.timerangepicker.TimeRangePicker
import timerx.buildStopwatch
import timerx.buildTimer
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class AdaptiveUIAnger : AppCompatActivity() {
    lateinit var image: ImageView
    lateinit var takeBreakText : TextView
    var isActionAlreadyInProgress = false
    lateinit var customCountdownTimer: CustomCountdownTimer
    lateinit var circularProgressBar : ProgressBar
    lateinit var timerButton : Button

    private var countdownTime = 60 // 1 hour , 3600 second, 60 min
    private val clockTime = (countdownTime * 1000).toLong()
    private val progressTime = (clockTime / 1000).toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive_uianger)

        takeBreakText = findViewById(R.id.angerText)
        circularProgressBar = findViewById(R.id.circularProgressBar)
        val rippleback = findViewById<RippleBackground>(R.id.ripple_animation)

        timerButton = findViewById(R.id.SelectTime)

        takeBreakText.visibility = TextView.INVISIBLE

        timerButton.setOnClickListener {
            if(!isActionAlreadyInProgress){
                CreateDialogBox()
            }
        }
        image = findViewById(R.id.shapableimage)

        var secondsLeft = 0
        customCountdownTimer = object : CustomCountdownTimer(clockTime, 1000) {}
        customCountdownTimer.onTick = { millisUntilFinished ->
            val second = (millisUntilFinished / 1000.0f).roundToInt()
            if (second != secondsLeft) {
                secondsLeft = second
                timerFormat(
                    secondsLeft,
                    takeBreakText
                )
            }
        }
        customCountdownTimer.onFinish = {
            isActionAlreadyInProgress = false
            takeBreakText.text = "Good Job"
            timerFormat(
                0,
                takeBreakText
            )
        }

        circularProgressBar.max = progressTime.toInt()
        circularProgressBar.progress = progressTime.toInt()
    }
    private fun timerFormat(secondsLeft: Int, timeTxt: TextView) {
        circularProgressBar.progress = secondsLeft
        if(secondsLeft == 0){
            timeTxt.text = "Good Job"
        }
        val decimalFormat = DecimalFormat("00")
        val hour = secondsLeft / 3600
        val min = (secondsLeft % 3600) / 60
        val seconds = secondsLeft % 60
        val timeFormat1 = decimalFormat.format(secondsLeft)
        val timeFormat2 = decimalFormat.format(min) + ":" + decimalFormat.format(seconds)
        val timeFormat3 =
            decimalFormat.format(hour) + ":" + decimalFormat.format(min) + ":" + decimalFormat.format(
                seconds
            )
        if(timeFormat3.equals("00:00:00")){
            timeTxt.text = "Good Job"
        }
        timeTxt.text = timeFormat3
    }

    fun CreateDialogBox(){
        countdownTime = 60
        val dialogview = LayoutInflater.from(this).inflate(R.layout.dialog_box,null)
        val builder = AlertDialog.Builder(this)
        with(builder){
            setView(dialogview)
        }
        val alertdialog = builder.show()
        val picker = alertdialog.findViewById<TimeRangePicker>(R.id.picker)
        val okBtn = alertdialog.findViewById<Button>(R.id.Ok)

        picker?.setOnTimeChangeListener(object : TimeRangePicker.OnTimeChangeListener {
            override fun onStartTimeChange(startTime: TimeRangePicker.Time) {
                Log.d("TimeRangePicker", "Start time: " + startTime.minute)
            }

            override fun onEndTimeChange(endTime: TimeRangePicker.Time) {
                Log.d("TimeRangePicker", "End time: " + endTime.minute)
            }

            override fun onDurationChange(duration: TimeRangePicker.TimeDuration) {
                Log.d("TimeRangePicker", "Duration: " + duration.durationMinutes)
            }
        })
        picker?.sliderColor = resources.getColor(R.color.soft_orange)

        picker?.sliderRangeColor = resources.getColor(R.color.soft_purple)

        okBtn?.setOnClickListener {
            isActionAlreadyInProgress = true
            customCountdownTimer.startTimer()
            takeBreakText.visibility = TextView.VISIBLE
            findViewById<RippleBackground>(R.id.ripple_animation).startRippleAnimation()
            alertdialog.dismiss()
        }
    }

    override fun onBackPressed() {
        if(!isActionAlreadyInProgress) {
            super.onBackPressed()
        }
    }
}

