package snow.app.eduhub.util

import android.widget.ImageView

interface LikeDislikeListener {
    fun likeClick(id:String,type:String, imageView_fav: ImageView,imageView_unfav: ImageView)

}