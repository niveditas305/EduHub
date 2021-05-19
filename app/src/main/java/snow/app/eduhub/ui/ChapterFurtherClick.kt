package snow.app.eduhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_continue_detail.*
import snow.app.eduhub.R
import kotlinx.android.synthetic.main.activity_topic_click_screen.*
import snow.app.eduhub.databinding.ActivityContinueDetailBinding
import snow.app.eduhub.databinding.ActivityTopicClickScreenBinding
import snow.app.eduhub.ui.adapter.ChapterDetailssAdapter
import snow.app.eduhub.ui.adapter.ChaptersAdapter
import snow.app.eduhub.ui.adapter.TopicDetailsAdapter
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.viewmodels.ChaptersVM
import snow.app.eduhub.viewmodels.TopicDetailsVm

class ChapterFurtherClick : BaseActivity() {


    lateinit var binding: ActivityTopicClickScreenBinding
    lateinit var viewModel: TopicDetailsVm
    private lateinit var mInterstitialAd: InterstitialAd
    lateinit var chapterDetailssAdapter: TopicDetailsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_topic_click_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topic_click_screen)
        binding.lifecycleOwner = this
        viewModel = TopicDetailsVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.isLoading.observe(this, Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel.isError.observe(this, Observer {
            if (it.isError) {
                showError(it.message, this);
            }

        })
        if (intent.hasExtra("teacherId")) {
            viewModel.chapter_id.set(intent.getStringExtra("chapterId"))
            viewModel.teacher_id.set(intent.getStringExtra("teacherId"))
            viewModel.subject_id.set(intent.getStringExtra("subjectId"))
            viewModel.chaptername.set(intent.getStringExtra("chaptername"))
        }





        if (isNetworkConnected()) {
            viewModel.getTopicDetailswithpdfs()
        } else {
            showInternetToast()
        }




        viewModel.respData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {

                    dialog.dismiss()
                    if (it.data.size > 0) {
                        val linearLayoutManagertut =
                            LinearLayoutManager(this)
                        binding.rvOne.layoutManager = linearLayoutManagertut
                        chapterDetailssAdapter = TopicDetailsAdapter(this, it.data, this)
                        binding.rvOne.adapter = chapterDetailssAdapter
                        binding.noRecordFound.visibility = View.GONE
                    } else {
                        binding.noRecordFound.visibility = View.VISIBLE
                    }


                } else {
                    Log.e("statusfalse", "login--")
                    showError(it.message, this)
                }
            }

        })



        if (isNetworkConnected()) {
//init adds
            initAdds()

            mInterstitialAd.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.


                    if (mInterstitialAd.isLoaded) {
                        mInterstitialAd.show()
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.")
                    }
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Code to be executed when an ad request fails.
                    Log.d("TAG", "The interstitial wasn't loaded onAdFailedToLoad.")
                }

                override fun onAdOpened() {
                    // Code to be executed when the ad is displayed.
                }

                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                override fun onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                override fun onAdClosed() {
                    // Code to be executed when the interstitial ad is closed.

                    //mInterstitialAd.loadAd(AdRequest.Builder().build())
                }
            }
        } else {
            showInternetToast()
        }





    }


    fun initAdds() {

        MobileAds.initialize(
            this,
            "ca-app-pub-3344875363675061~5961754024"
        )

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712" //test key
        //  mInterstitialAd.adUnitId = "ca-app-pub-3344875363675061/2836951139" //live key
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }
}