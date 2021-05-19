package snow.app.eduhub.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import snow.app.eduhub.MainActivity
import snow.app.eduhub.R
import snow.app.eduhub.util.BaseActivity

class Splash : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val handler = Handler()
        handler.postDelayed({
            //  if (appSession.getAppData() != null) {
           /* startActivity(Intent(this@Splash, WelcomeSignupScreen::class.java))
            finishAffinity()*/
            /*  } else {
                  startActivity(Intent(this@Splash, WelcomeScreen::class.java))
                  finishAffinity()
              }*/





            if (getSession()?.getAppData() != null) {
             //   Log.v("confirmed", getSession()?.getAppData()?.data?.studentStatus.toString())
                if (getSession()?.getAppData()?.data?.studentStatus == 0) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity()
                    } else {
                        finish()
                    }
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity()
                    } else {
                        finish()
                    }
                }

            } else {
                startActivity(Intent(this, WelcomeSignupScreen::class.java))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity()
                } else {
                    finish()
                }

            }



        }, 1000)
        //}
    }
}