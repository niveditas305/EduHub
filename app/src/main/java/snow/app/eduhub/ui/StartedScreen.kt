package snow.app.eduhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
 import kotlinx.android.synthetic.main.activity_started_screen.*
import snow.app.eduhub.R

class StartedScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_started_screen)
        tv_started.setOnClickListener {
            startActivity(Intent(this,WelcomeSignupScreen::class.java))
        }
    }
}