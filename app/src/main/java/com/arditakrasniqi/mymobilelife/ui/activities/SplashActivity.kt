package com.arditakrasniqi.mymobilelife.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveInitializer
import app.rive.runtime.kotlin.core.Rive
import com.arditakrasniqi.mymobilelife.R
import com.arditakrasniqi.mymobilelife.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    var SPLASH_TIME: Long = 3000
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        setProgressBar()
        setUpSplash()
    }

    private fun setUpSplash() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }, SPLASH_TIME)

    }

    private fun setProgressBar() {
        AppInitializer.getInstance(applicationContext)
            .initializeComponent(RiveInitializer::class.java)
        Rive.init(this)

        binding.rvAnimation.setRiveResource(
            R.raw.load_bar,
            artboardName = "V1 more dots with stream",
            autoplay = false
        )

        object : CountDownTimer(SPLASH_TIME, 10) {
            override fun onTick(millisUntilFinished: Long) {
                var timeSpent = SPLASH_TIME - millisUntilFinished
                val remainingTime = timeSpent.toFloat() / SPLASH_TIME.toFloat()
                val percentage: Float = remainingTime * 100
                binding.rvAnimation.setNumberState("State Machine 1", "Imput", percentage)
            }

            override fun onFinish() {}
        }.start()
    }
}