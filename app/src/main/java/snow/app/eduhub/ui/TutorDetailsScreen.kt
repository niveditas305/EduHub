package snow.app.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import snow.app.eduhub.R
import kotlinx.android.synthetic.main.activity_tutor_details_screen.*

class TutorDetailsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_details_screen)

        iv_back.setOnClickListener {
            onBackPressed()
        }
    }
}