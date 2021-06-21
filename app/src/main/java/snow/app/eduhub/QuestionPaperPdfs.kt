package snow.app.eduhub

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.metadata.MediationMetaData
import com.unity3d.ads.metadata.MetaData
import com.unity3d.ads.metadata.PlayerMetaData
import com.unity3d.services.banners.IUnityBannerListener
import com.unity3d.services.banners.UnityBanners
import com.unity3d.services.banners.view.BannerPosition
import com.unity3d.services.core.api.Sdk
import com.unity3d.services.core.misc.Utilities
import com.unity3d.services.core.properties.SdkProperties
import com.unity3d.services.core.webview.WebView
import snow.app.eduhub.ui.adapter.WorksheetAdapter
import kotlinx.android.synthetic.main.activity_question_paper_pdfs.*
import kotlinx.android.synthetic.main.activity_teacher_profile.iv_back
import snow.app.eduhub.databinding.ActivityQuestionBankCategoryScreenBinding
import snow.app.eduhub.databinding.ActivityQuestionPaperPdfsBinding
import snow.app.eduhub.ui.adapter.PastQueCatAdapter
import snow.app.eduhub.ui.adapter.PastQuestionpprAdapter
import snow.app.eduhub.ui.fragments.HomeFragment
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.util.PdfClickInterface
import snow.app.eduhub.viewmodels.PastQuestionpprpdfsVm
import snow.app.eduhub.viewmodels.QuestionbankcategoryVm

class QuestionPaperPdfs : BaseActivity(),PdfClickInterface, IUnityAdsListener ,
    IUnityBannerListener {

    lateinit var viewModel: PastQuestionpprpdfsVm
    private val defaultGameId = "4169571"
    var mUrl:String?=""
    lateinit var binding: ActivityQuestionPaperPdfsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_question_paper_pdfs)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_question_paper_pdfs)
        binding.lifecycleOwner = this
        viewModel = PastQuestionpprpdfsVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        UnityBanners.destroy()
        Sdk.reinitialize(null)
        initAds()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        if (intent.hasExtra("subject_id")) {

            viewModel.subject_id.set(intent.getStringExtra("subject_id"))
           // viewModel.past_question_category_id.set(intent.getStringExtra("past_question_category_id"))
            //viewModel.past_question_category_name.set(intent.getStringExtra("past_question_category_name"))

        //    binding.title.setText(intent.getStringExtra("past_question_category_name"))


            if (isNetworkConnected()) {
                viewModel.fetchPastQuestionpprs(intent.getStringExtra("class_id").toString(),
                    intent.getStringExtra("subject_id").toString()
            /*        ,intent.getStringExtra("past_question_category_id").toString()*/)
            } else {
                showInternetToast()
            }
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

        viewModel.respData.observe(this, Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()


                    if (it.data.size > 0) {
                        val linearLayoutManagertut = LinearLayoutManager(this)
                        binding.rvPprPast.layoutManager = linearLayoutManagertut
                        var pastQueCatAdapter = PastQuestionpprAdapter(this, it.data, this,this)
                        binding.rvPprPast.adapter = pastQueCatAdapter


                        binding.noRecordFound.visibility = View.GONE


                    } else {
                        binding.noRecordFound.visibility = View.VISIBLE

                    }
                } else {
                    Log.e("statusfalse", "login--")
                    binding.noRecordFound.visibility = View.VISIBLE
                    //  showError(it.message, this)
                }
            }

        })



    }




    fun initAds() {


        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        UnityAds.addListener(this)
        UnityAds.setDebugMode(true)

        val mediationMetaData = MediationMetaData(this)
        mediationMetaData.setName("mediationPartner")
        mediationMetaData.setVersion("v12345")
        mediationMetaData.commit()

        val debugMetaData =
            MetaData(this)
        debugMetaData["test.debugOverlayEnabled"] = true
        debugMetaData.commit()
        val gameId: String = defaultGameId
        if (gameId.isEmpty()) {
            Toast.makeText(
                this,
                "Missing game id",
                Toast.LENGTH_SHORT
            ).show()

        }

        // disableButton(initializeButton)
        //   gameIdEdit.setEnabled(false)
        // testModeCheckBox.setEnabled(false)

        //   statusText.setText("Initializing...")
        UnityAds.addListener(this)
        UnityAds.initialize(
            this,
            gameId,
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
            })

        // store entered gameid in app settings

        // store entered gameid in app settings



    }
    override fun onSubmitClick(url: String, type: String) {
        mUrl=url
        val playerMetaData = PlayerMetaData(this)
        playerMetaData.setServerId("rikshot")
        playerMetaData.commit()

        val ordinalMetaData = MediationMetaData(this)
        ordinalMetaData.setOrdinal(HomeFragment.ordinal++)
        ordinalMetaData.commit()
        UnityAds.show(this, "Interstitial_Android")
    }

    override fun onUnityAdsStart(placementId: String?) {

    }

    override fun onUnityAdsFinish(placementId: String?, result: UnityAds.FinishState?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mUrl))
        startActivity(browserIntent)
    }

    override fun onUnityAdsError(error: UnityAds.UnityAdsError?, message: String?) {

    }

    override fun onUnityAdsReady(zoneId: String?) {
        Log.e("ready",zoneId.toString());
        if(zoneId.equals("Banner_Android"))
        {
            /*  UnityBanners.setBannerPosition(BannerPosition.BOTTOM_CENTER)
              UnityBanners.setBannerListener(this)
              UnityBanners.loadBanner(activity, zoneId)*/
//    UnityBanners.destroy()
            UnityBanners.setBannerPosition(BannerPosition.BOTTOM_CENTER)
          //  UnityBanners.setBannerListener(this)
            UnityBanners.loadBanner(this, "Banner_Android")
        }




            //..................
        Utilities.runOnUiThread {

            if (zoneId.equals("Banner_Android")) {
                /*  UnityBanners.setBannerPosition(BannerPosition.BOTTOM_CENTER)
                  UnityBanners.setBannerListener(this)
                  UnityBanners.loadBanner(activity, zoneId)*/
//    UnityBanners.destroy()
                UnityBanners.setBannerPosition(BannerPosition.BOTTOM_CENTER)
                UnityBanners.setBannerListener(this)
                UnityBanners.loadBanner(this, "Banner_Android")
            }
            if (zoneId.equals("Interstitial_Android")) {
                HomeFragment.interstitialPlacementId = zoneId
                /*   val playerMetaData = PlayerMetaData(activity)
                   playerMetaData.setServerId("rikshot")
                   playerMetaData.commit()

                   val ordinalMetaData = MediationMetaData(activity)
                   ordinalMetaData.setOrdinal(ordinal++)
                   ordinalMetaData.commit()

                   UnityAds.show(activity, interstitialPlacementId) */
//    UnityBanners.destroy()
/*    UnityBanners.setBannerPosition(BannerPosition.BOTTOM_CENTER)
    UnityBanners.setBannerListener(this)
    UnityBanners.loadBanner(activity, "Banner_Android")*/
            }
            Log.e("ready", "--" + zoneId)
            // look for various default placement ids over time
        }
        when (zoneId) {
            "video", "defaultZone", "defaultVideoAndPictureZone" -> {
                HomeFragment.interstitialPlacementId = zoneId
                val playerMetaData = PlayerMetaData(this)
                playerMetaData.setServerId("rikshot")
                playerMetaData.commit()

                val ordinalMetaData = MediationMetaData(this)
                ordinalMetaData.setOrdinal(HomeFragment.ordinal++)
                ordinalMetaData.commit()

                UnityAds.show(this, HomeFragment.interstitialPlacementId)
                // enableButton(interstitialButton)
            }
            "rewardedVideo", "rewardedVideoZone", "incentivizedZone" -> {
                // incentivizedPlacementId = zoneId
                //   enableButton(incentivizedButton)
            }
        }


        //  toast("Ready", zoneId)











    }

    override fun onUnityBannerLoaded(placementId: String?, view: View?) {
        Log.e("banner ", "--" + placementId)
        Utilities.runOnUiThread {
            if (view != null) {
                if (view.getParent() != null) {
                    if (view != null) {
                        (view.getParent() as ViewGroup).removeView(view)
                    } // <- fix
                }
            }
             addContentView(view, view?.layoutParams)

        }
    }

    override fun onUnityBannerShow(placementId: String?) {
     }

    override fun onUnityBannerClick(placementId: String?) {
     }

    override fun onUnityBannerHide(placementId: String?) {
     }

    override fun onUnityBannerError(message: String?) {
     }

    override fun onUnityBannerUnloaded(placementId: String?) {
     }

    override fun onDestroy() {
        super.onDestroy()
        UnityBanners.destroy()
    }
}