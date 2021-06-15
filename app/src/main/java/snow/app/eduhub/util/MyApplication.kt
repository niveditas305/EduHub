package snow.app.eduhub.util

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.UnityAds


class MyApplication : Application() {
     override fun onCreate() {
        super.onCreate()
        instance = this
       //  FirebaseApp.initializeApp(this)
  /*       UnityAds.initialize(
             this,
             "4169571",
             false,
             object : IUnityAdsInitializationListener {
                 override fun onInitializationComplete() {
                     Log.e("Unity status","Initialization Complete")
                 }

                 override fun onInitializationFailed(
                     error: UnityAds.UnityAdsInitializationError,
                     message: String
                 ) {
                     Log.e("Unity status","Initialization  --"+ message)
                 }
             })*/
         MobileAds.initialize(this) {}
   }




    companion object {
        @get:Synchronized
        var instance: MyApplication? = null
            private set
    }
}