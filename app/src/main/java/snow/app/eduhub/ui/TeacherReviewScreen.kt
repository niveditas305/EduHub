package snow.app.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import snow.app.eduhub.R
import snow.app.eduhub.ui.adapter.RatingReviewAdapter
import kotlinx.android.synthetic.main.activity_teacher_review_screen.*

class TeacherReviewScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_review_screen)

        iv_back.setOnClickListener {
            onBackPressed()
        }


    /*    val linearLayoutManager = LinearLayoutManager(this)
        //binding.rvHome.setLayoutManager(linearLayoutManager)
        rv_review.layoutManager=linearLayoutManager
        val ratingReviewAdapter = RatingReviewAdapter(this)
        rv_review.adapter=ratingReviewAdapter*/
    }
}