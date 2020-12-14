package snow.app.eduhub.util

import android.widget.ImageView
import snow.app.eduhub.ui.network.responses.testquestionsres.Question

interface GetPositionOnBackInterface {
    fun likeClick(pos: Int ,list:List<Question>)

}