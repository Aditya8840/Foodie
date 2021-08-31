package com.codiyapa.foodie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if(auth.currentUser == null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        Glide
            .with(this)
            .load(getDrawable(R.drawable.boysplash))
            .into(boysImage)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            if(auth.currentUser != null) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }, 2500)
    }
}