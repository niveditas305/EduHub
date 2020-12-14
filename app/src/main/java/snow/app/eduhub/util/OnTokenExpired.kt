package snow.app.eduhub.util

import android.location.Location


class OnTokenExpired {

    interface OnTokenExpiredListener {

        fun onTokenExpiredListener()
    }

    companion object{
        var onTokenExpiredListener: OnTokenExpired.OnTokenExpiredListener? = null

        fun setOnTokenListener(listener: OnTokenExpired.OnTokenExpiredListener?) {
            onTokenExpiredListener = listener
        }
    }
}