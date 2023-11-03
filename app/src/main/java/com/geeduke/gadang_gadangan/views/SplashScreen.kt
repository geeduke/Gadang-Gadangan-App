package com.geeduke.gadang_gadangan.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.geeduke.gadang_gadangan.R
import com.geeduke.gadang_gadangan.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private var _binding:ActivitySplashScreenBinding?=null
    private val binding get()= _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
        },1000)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}