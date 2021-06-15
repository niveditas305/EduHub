package snow.app.eduhub.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.unity3d.services.core.api.Sdk
import com.unity3d.services.core.webview.WebView
import snow.app.eduhub.R
import snow.app.eduhub.databinding.FragmentStudyGuideBinding
import snow.app.eduhub.ui.adapter.StudyGuidePdfAdapter
import snow.app.eduhub.ui.adapter.StudyGuideVideoAdapter
import snow.app.eduhub.ui.fragments.HomeFragment.Companion.ordinal
import snow.app.eduhub.ui.network.responses.getstudyguild.Data
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.util.PdfClickInterface
import snow.app.eduhub.viewmodels.StudyGuideVm

class StudyGuideActivity : BaseActivity(), IUnityAdsListener, PdfClickInterface {

    private var mType: String?=""
    private var mUrl: String?=""
    lateinit var viewModel: StudyGuideVm
    lateinit var binding: FragmentStudyGuideBinding
    var studyguideList: ArrayList<Data> = ArrayList()
    var gradeid = ""
    private val defaultGameId = "4169571"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_study_guide)
        binding.lifecycleOwner = this
        viewModel = StudyGuideVm(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }



        if (intent.hasExtra("gradeid")) {
            gradeid = intent.getStringExtra("gradeid").toString()

            if (isNetworkConnected()) {
                viewModel?.getStudyGuide(gradeid)
            } else {
                showInternetToast()
            }
        }



        Sdk.reinitialize( null)
        initAds()


        observers()
    }

    override fun onResume() {
        super.onResume()
       // Sdk.reinitialize( null)
      //  initAds()
    }
    override fun onStart() {
        super.onStart()

       /* val playerMetaData = PlayerMetaData(this)
        playerMetaData.setServerId("rikshot")
        playerMetaData.commit()

        val ordinalMetaData = MediationMetaData(this)
        ordinalMetaData.setOrdinal(ordinal++)
        ordinalMetaData.commit()

        UnityAds.show(this, "Interstitial_Android")*/
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
    fun observers() {
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
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    studyguideList.clear()
                    studyguideList.addAll(it.data)
                    setVideoAdapter(studyguideList)
                    setpdfAdapter(studyguideList)
                } else {
                    dialog.dismiss()
                    showError(it.message, this)
                }
            }

        })


    }

    fun setVideoAdapter(studyguideList: ArrayList<Data>) {
        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvVideos.layoutManager = linearLayoutManager
        val tutorialsAdapter = StudyGuideVideoAdapter(this, studyguideList,this)
        binding.rvVideos.adapter = tutorialsAdapter
    }

    fun setpdfAdapter(studyguideList: ArrayList<Data>) {
        val linearLayoutManager_worksheet = LinearLayoutManager(this)
        binding.rvChapters.layoutManager = linearLayoutManager_worksheet
        val worksheetAdapter = StudyGuidePdfAdapter(this, studyguideList,this)
        binding.rvChapters.adapter = worksheetAdapter


    }

    override fun onUnityAdsStart(placementId: String?) {

    }

    override fun onUnityAdsFinish(placementId: String?, result: UnityAds.FinishState?) {
        Log.e("finish","yes")


        if(mType.equals("video")) {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // intent.setPackage("com.google.android.youtube");
            startActivity(intent)
        }
        else{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mUrl))
            startActivity(browserIntent)

        }
       // Sdk.reinitialize( null)
     //   initAds()
    }

    override fun onUnityAdsError(error: UnityAds.UnityAdsError?, message: String?) {

    }

    override fun onUnityAdsReady(placementId: String?) {
        Log.e("ready","--"+ placementId)
        if(placementId.equals("Interstitial_Android"))
        {


//    UnityBanners.destroy()
/*    UnityBanners.setBannerPosition(BannerPosition.BOTTOM_CENTER)
    UnityBanners.setBannerListener(this)
    UnityBanners.loadBanner(activity, "Banner_Android")*/
        }
    }

    override fun onSubmitClick(url: String, s: String) {
        mUrl=url
        mType=s
       // Sdk.reinitialize( null)
        val playerMetaData = PlayerMetaData(this)
        playerMetaData.setServerId("rikshot")
        playerMetaData.commit()

        val ordinalMetaData = MediationMetaData(this)
        ordinalMetaData.setOrdinal(ordinal++)
        ordinalMetaData.commit()
        UnityAds.show(this, "Interstitial_Android")
    }


}