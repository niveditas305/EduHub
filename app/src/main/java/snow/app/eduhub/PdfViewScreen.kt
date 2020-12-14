package snow.app.eduhub

import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_pdf_view_screen.*
import snow.app.eduhub.util.BaseActivity

class PdfViewScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view_screen)
        if (intent.hasExtra("path")) {

            // pdfView.fromUri(intent.getStringExtra("path")?.toUri())
            webview.getSettings().setLoadsImagesAutomatically(true);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
             webview.loadUrl(intent.getStringExtra("path")!!);
        }
    }
}