package com.example.eduhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.eduhub.MainActivity
import com.example.eduhub.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val handler = Handler()
        handler.postDelayed({
            //  if (appSession.getAppData() != null) {
            startActivity(Intent(this@Splash, WelcomeActivity::class.java))
            finishAffinity()
            /*  } else {
                  startActivity(Intent(this@Splash, WelcomeScreen::class.java))
                  finishAffinity()
              }*/
        }, 3000)
        //}
    }
}