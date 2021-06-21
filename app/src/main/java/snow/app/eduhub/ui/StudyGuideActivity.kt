package snow.app.eduhub.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
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
import com.unity3d.services.core.webview.WebView
import snow.app.eduhub.R
import snow.app.eduhub.databinding.FragmentStudyGuideBinding
import snow.app.eduhub.ui.adapter.StudyGuidePdfAdapter
import snow.app.eduhub.ui.adapter.StudyGuideVideoAdapter
import snow.app.eduhub.ui.fragments.HomeFragment
import snow.app.eduhub.ui.fragments.HomeFragment.Companion.ordinal
import snow.app.eduhub.ui.network.responses.getstudyguild.Data
import snow.app.eduhub.util.BaseActivity
import snow.app.eduhub.util.PdfClickInterface
import snow.app.eduhub.viewmodels.StudyGuideVm

class StudyGuideActivity : BaseActivity(), IUnityAdsListener, PdfClickInterface ,
    IUnityBannerListener {

    private var mType: String? = ""
    private var mUrl: String? = ""
    lateinit var viewModel: StudyGuideVm
    lateinit var binding: FragmentStudyGuideBinding
    var studyguidePdfList: ArrayList<Data> = ArrayList()
    var studyguideVideoList: ArrayList<Data> = ArrayList()
    var gradeid = ""
    var subject_id = ""
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
            subject_id = intent.getStringExtra("subject_id").toString()

            if (isNetworkConnected()) {
                viewModel.getStudyGuide(gradeid, subject_id, binding.edSearchHome.text.toString())
            } else {
                showInternetToast()
            }
        }

        UnityBanners.destroy()
        listners()
        Sdk.reinitialize(null)
        initAds()


        observers()
    }

    fun listners() {

        binding.ivSearchHome.setOnClickListener {
            if (binding.edSearchHome.text.toString().length != 0) {
                if (isNetworkConnected()) {
                    viewModel.getStudyGuide(
                        gradeid,
                        subject_id,
                        binding.edSearchHome.text.toString()
                    )
                } else {
                    showInternetToast()
                }
            }
        }



        binding.edSearchHome.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                //   if (ed_search_home_global?.text.toString().length != 0) {
                /* if (ed_search_home_global?.text.toString().length >= 3) {

                     viewModel.homeClassApi(
                         activityy!!,
                         ed_search_home_global?.text!!.toString(),
                         getLoginUserId(),
                         getSession()?.getLatitude()!!,
                         getSession()?.getLongitude()!!
                     )
                 } else*/ if (binding.edSearchHome?.text.toString().length == 0) {
                    if (isNetworkConnected()) {
                        viewModel.getStudyGuide(gradeid, subject_id, "")
                    } else {
                        showInternetToast()
                    }
                } else {

                }

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
        binding.edSearchHome.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(
                v: TextView?,
                actionId: Int,
                event: KeyEvent?
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {


                    if (binding.edSearchHome.text.toString().length != 0) {

                        if (isNetworkConnected()) {
                            viewModel.getStudyGuide(
                                gradeid,
                                subject_id,
                                binding.edSearchHome.text.toString()
                            )
                        } else {
                            showInternetToast()
                        }
                    }


                    return true
                }
                return false
            }
        })


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
                    Log.e("Unity status", "Initialization Complete")
                }

                override fun onInitializationFailed(
                    error: UnityAds.UnityAdsInitializationError,
                    message: String
                ) {
                    Log.e("Unity status", "Initialization  --" + message)
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
                    studyguidePdfList.clear()
                    studyguideVideoList.clear()

                    for (i in 0 until it.data.size) {
                        if (!it.data.get(i).studyPdf.isEmpty()) {
                            studyguidePdfList.add(it.data.get(i))
                        }

                        if (!it.data.get(i).videoPath.isNullOrEmpty()) {
                            studyguideVideoList.add(it.data.get(i))
                        }


                    }


                    if (studyguideVideoList.size == 0) {
                        binding.noRecordFound.visibility = View.VISIBLE
                        binding.rvVideos.visibility = View.GONE

                    } else {
                        binding.noRecordFound.visibility = View.GONE
                        binding.rvVideos.visibility = View.VISIBLE
                    }





                    if (studyguidePdfList.size == 0) {
                        binding.tvNoRecordFound.visibility = View.VISIBLE
                        binding.rvChapters.visibility = View.GONE
                    } else {
                        binding.tvNoRecordFound.visibility = View.GONE
                        binding.rvChapters.visibility = View.VISIBLE
                    }


                    setVideoAdapter(studyguideVideoList)
                    setpdfAdapter(studyguidePdfList)
                } else {
                    dialog.dismiss()
                    // showError(it.message, this)

                    binding.noRecordFound.visibility = View.VISIBLE
                    binding.rvVideos.visibility = View.GONE

                    binding.tvNoRecordFound.visibility = View.VISIBLE
                    binding.rvChapters.visibility = View.GONE
                }
            }

        })


    }

    fun setVideoAdapter(studyguideList: ArrayList<Data>) {
        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvVideos.layoutManager = linearLayoutManager
        val tutorialsAdapter = StudyGuideVideoAdapter(this, studyguideList, this)
        binding.rvVideos.adapter = tutorialsAdapter
    }

    fun setpdfAdapter(studyguideList: ArrayList<Data>) {
        val linearLayoutManager_worksheet = LinearLayoutManager(this)
        binding.rvChapters.layoutManager = linearLayoutManager_worksheet
        val worksheetAdapter = StudyGuidePdfAdapter(this, studyguideList, this)
        binding.rvChapters.adapter = worksheetAdapter


    }

    override fun onUnityAdsStart(placementId: String?) {

    }

    override fun onUnityAdsFinish(placementId: String?, result: UnityAds.FinishState?) {
        Log.e("finish", "yes")


        if (mType.equals("video")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // intent.setPackage("com.google.android.youtube");
            startActivity(intent)


        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mUrl))
            startActivity(browserIntent)

        }
        // Sdk.reinitialize( null)
        //   initAds()
    }

    override fun onUnityAdsError(error: UnityAds.UnityAdsError?, message: String?) {

    }

    override fun onUnityAdsReady(zoneId: String?) {
        Log.e("ready", "--" + zoneId)
        if (zoneId.equals("Interstitial_Android")) {


//    UnityBanners.destroy()
/*    UnityBanners.setBannerPosition(BannerPosition.BOTTOM_CENTER)
    UnityBanners.setBannerListener(this)
    UnityBanners.loadBanner(activity, "Banner_Android")*/
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
                UnityBanners.loadBanner(this@StudyGuideActivity, "Banner_Android")
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
                val playerMetaData = PlayerMetaData(this@StudyGuideActivity)
                playerMetaData.setServerId("rikshot")
                playerMetaData.commit()

                val ordinalMetaData = MediationMetaData(this@StudyGuideActivity)
                ordinalMetaData.setOrdinal(HomeFragment.ordinal++)
                ordinalMetaData.commit()

                UnityAds.show(this@StudyGuideActivity, HomeFragment.interstitialPlacementId)
                // enableButton(interstitialButton)
            }
            "rewardedVideo", "rewardedVideoZone", "incentivizedZone" -> {
                // incentivizedPlacementId = zoneId
                //   enableButton(incentivizedButton)
            }
        }


        //  toast("Ready", zoneId)









    }

    override fun onSubmitClick(url: String, s: String) {
        mUrl = url
        mType = s

        // Sdk.reinitialize( null)
        val playerMetaData = PlayerMetaData(this)
        playerMetaData.setServerId("rikshot")
        playerMetaData.commit()

        val ordinalMetaData = MediationMetaData(this)
        ordinalMetaData.setOrdinal(ordinal++)
        ordinalMetaData.commit()
        UnityAds.show(this, "Interstitial_Android")
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