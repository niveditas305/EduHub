package snow.app.eduhub.util

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp


class MyApplication : Application() {
     override fun onCreate() {
        super.onCreate()
        instance = this
       //  FirebaseApp.initializeApp(this)

         MobileAds.initialize(this) {}
   }




    companion object {
        @get:Synchronized
        var instance: MyApplication? = null
            private set
    }
}