package snow.app.eduhub.ui.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAds.UnityAdsInitializationError
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
import snow.app.eduhub.R
import snow.app.eduhub.SearchScreen
import snow.app.eduhub.SeeAllTeacherListingScreen
import snow.app.eduhub.databinding.FragmentHome2Binding
import snow.app.eduhub.ui.TeacherListingScreen
import snow.app.eduhub.ui.TopicClicks
import snow.app.eduhub.ui.adapter.HomeTopPickAdapter
import snow.app.eduhub.ui.adapter.MyViewPagerAdapter
import snow.app.eduhub.ui.adapter.SubjectsAdapter
import snow.app.eduhub.ui.adapter.TeachersAdapter
import snow.app.eduhub.ui.network.responses.homedatares.*
import snow.app.eduhub.util.BaseFragment
import snow.app.eduhub.util.LikeDislikeListener
import snow.app.eduhub.util.OnTokenExpired
import snow.app.eduhub.viewmodels.BaseViewModel
import snow.app.eduhub.viewmodels.HomeViewModel


class HomeFragment : BaseFragment(), LikeDislikeListener,
    OnTokenExpired.OnTokenExpiredListener, IUnityAdsListener, IUnityBannerListener {

    private val defaultGameId = "4169571"
    private var bannerShowing = false

    var image_resources1 = intArrayOf(
        R.drawable.bannernew,
        R.drawable.bannernew,
        R.drawable.bannernew,
        R.drawable.bannernew,
        R.drawable.bannernew,
        R.drawable.bannernew
    )
    lateinit var pager: ViewPager
    lateinit var rv_top_home: RecyclerView
    lateinit var dots: TabLayout
    lateinit var tv_main_two: LinearLayout
    var mmContext: Context? = null
    var list: ArrayList<TopTeacher> = ArrayList<TopTeacher>()
    lateinit var tv_one: LinearLayout
    var topteacherlist: ArrayList<TopTeacher> = ArrayList()
    var toptopicslist: ArrayList<TopTopic> = ArrayList()
    var subjectlist: ArrayList<Subject> = ArrayList()
    var bannersList: ArrayList<Banner> = ArrayList()
    var viewModel: HomeViewModel? = null
    lateinit var binding: FragmentHome2Binding

    companion object {
        var interstitialPlacementId: String? = ""
        var ordinal = 1
        var mContextt: Activity? = null;
        fun playVideoAd() {
            val playerMetaData = PlayerMetaData(mContextt)
            playerMetaData.setServerId("rikshot")
            playerMetaData.commit()

            val ordinalMetaData = MediationMetaData(mContextt)
            ordinalMetaData.setOrdinal(ordinal++)
            ordinalMetaData.commit()

            UnityAds.show(mContextt, "Interstitial_Android")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home2, container, false);
        binding.lifecycleOwner = this
        viewModel = HomeViewModel(getUserToken())
        binding.viewModel = viewModel
        binding.executePendingBindings()
        dialog = ProgressDialog(context)
        dialog.setMessage("Please wait...")
        dialog.setCancelable(false)
        viewModell = BaseViewModel()
        mContextt = context as Activity?
        initAds()
        viewModel?.isLoading?.observe(requireActivity(), Observer {
            if (it) {
                dialog.show()
            } else {
                dialog.hide()
            }
        })
        viewModel?.isError?.observe(requireActivity(), Observer {
            if (it.isError) {
                showError(it.message, requireContext());
            }

        })
        // Inflate the layout for this fragment
        //  val view = inflater.inflate(R.layout.fragment_home2, container, false)


        binding.ivBanner.setOnClickListener {
            /*      if ( bannerShowing) {
                      UnityBanners.destroy()

                       bannerShowing = false
                  } else {
                      UnityBanners.setBannerPosition(BannerPosition.BOTTOM_CENTER)
                      UnityBanners.setBannerListener(this)
                      UnityBanners.loadBanner(activity, "Banner_Android")
                  }
      */

        }
        binding.tvMainTwo.setOnClickListener {
            startActivity(Intent(requireContext(), TeacherListingScreen::class.java))
        }
        binding.tvOne.setOnClickListener {
            startActivity(Intent(requireContext(), TeacherListingScreen::class.java))
        }

        binding.tvSeeAll.setOnClickListener {
            val intent: Intent = Intent(requireContext(), SeeAllTeacherListingScreen::class.java)
            intent.putExtra(
                "fromfilte" +
                        "r", "filter"
            )
            intent.putExtra("name", "Salons")
            val bundle = Bundle()
            val gson = Gson()
            val jsonDetails = gson.toJson(list)
            bundle.putString("salonlist", jsonDetails)
            intent.putExtras(bundle)
            startActivity(intent)

        }

        binding.fSearch.setOnClickListener {
            startActivity(Intent(requireContext(), SearchScreen::class.java))
        }

        //fetch home data response
        viewModel?.reshome?.observe(requireActivity(), Observer {
            Log.e("respData ", "login--")
            if (it != null) {

                var data: HomeDataRes = it
                if (it.status) {
                    dialog.dismiss()
                    //clearlist
                    clearList()
                    list.clear()
                    list.addAll(it.data.topTeacher)

                    //set recently and countinue

                    if (it.data.recentLearnTopic != null) {
                        viewModel?.recently_learned?.set(it.data.recentLearnTopic.topicName)
                    } else {
                        binding.llOne.visibility = View.GONE
                    }
                    if (it.data.continueTopic != null) {
                        viewModel?.countinue_topic?.set(it.data.continueTopic.topicName)
                    } else {
                        binding.llTwo.visibility = View.GONE
                    }




                    binding.llTwo.setOnClickListener {


                        var intent: Intent = Intent(requireContext(), TopicClicks::class.java)
                        intent.putExtra("chapterId", data.data.continueTopic.chapterId.toString())
                        intent.putExtra("teacherId", data.data.continueTopic.teacherId.toString())
                        intent.putExtra("subjectId", data.data.continueTopic.subjectId.toString())
                        intent.putExtra("topic_id", data.data.continueTopic.id.toString())
                        startActivity(intent)
                    }
                    binding.llOne.setOnClickListener {


                        var intent: Intent = Intent(requireContext(), TopicClicks::class.java)
                        intent.putExtra(
                            "chapterId",
                            data.data.recentLearnTopic.chapterId.toString()
                        )
                        intent.putExtra(
                            "teacherId",
                            data.data.recentLearnTopic.teacherId.toString()
                        )
                        intent.putExtra(
                            "subjectId",
                            data.data.recentLearnTopic.subjectId.toString()
                        )
                        intent.putExtra("topic_id", data.data.recentLearnTopic.id.toString())
                        startActivity(intent)
                    }

                    //add data to the list
                    subjectlist.addAll(it.data.subject)
                    topteacherlist.addAll(it.data.topTeacher)
                    toptopicslist.addAll(it.data.topTopics)
                    bannersList.addAll(it.data.banner)


                    //set view pager adapter

                    if (bannersList.size != 0) {
                        val adapter = MyViewPagerAdapter(mmContext, bannersList)
                        binding.pager.adapter = adapter
                        binding.dots.setupWithViewPager(binding.pager, true) // <- magic here
                    } else {
                        binding.pager.visibility = View.GONE
                    }


//set data in subject adapter
                    val linearLayoutManager = GridLayoutManager(
                        mmContext, 2

                    )

                    binding.rvSubjects.layoutManager = linearLayoutManager
                    val subjectsAdapter = SubjectsAdapter(requireContext(), subjectlist)
                    binding.rvSubjects.adapter = subjectsAdapter


                    // set data in top topics
                    val linearLayoutManager_topics = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL, false
                    )

                    binding.rvTopHome.layoutManager = linearLayoutManager_topics
                    val homeTopPickAdapter = HomeTopPickAdapter(
                        requireContext(),
                        toptopicslist,
                        this
                    )
                    binding.rvTopHome.adapter = homeTopPickAdapter
                    // showSuccess(it.message,requireContext())

                    //setdataintopteachers
                    val linearLayoutManager_teachers = GridLayoutManager(requireContext(), 2)
                    binding.rvTopteachers.layoutManager = linearLayoutManager_teachers
                    val teacherAdapter = TeachersAdapter(requireContext(), topteacherlist, this)
                    binding.rvTopteachers.adapter = teacherAdapter

                } else {
                    Log.e("statusfalse", "login--")
                    dialog.dismiss()
                    showError(it.message, requireContext())
                }
            }

        })


        // fetch fav // unfav rsponse
        viewModel?.res_favunfav?.observe(requireActivity(), Observer {
            Log.e("respData ", "login--")
            if (it != null) {
                if (it.status) {
                    dialog.dismiss()
                    showToast(it.message)
                } else {
                    dialog.dismiss()
                    Log.e("statusfalse", "login--")
                    showToast(it.message)
                }
            }

        })


        return binding.root
    }


    fun clearList() {
        subjectlist.clear()
        topteacherlist.clear()
        toptopicslist.clear()
        bannersList.clear()
    }


    fun initAds() {
        if (SdkProperties.isInitialized()) {
            Sdk.reinitialize(null)
        }
        val preferences =
            requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        //gameIdEdit.setText(preferences.getString("gameId", defaultGameId))
        //  testModeCheckBox.setChecked(true)

        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        UnityAds.addListener(this)
        UnityAds.setDebugMode(true)

        val mediationMetaData = MediationMetaData(activity)
        mediationMetaData.setName("mediationPartner")
        mediationMetaData.setVersion("v12345")
        mediationMetaData.commit()

        val debugMetaData =
            MetaData(activity)
        debugMetaData["test.debugOverlayEnabled"] = true
        debugMetaData.commit()
        val gameId: String = defaultGameId
        if (gameId.isEmpty()) {
            Toast.makeText(
                context,
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
            context,
            "4169571",
            false,
            object : IUnityAdsInitializationListener {
                override fun onInitializationComplete() {
                    Log.e("Unity status", "Initialization Complete")
                }

                override fun onInitializationFailed(
                    error: UnityAdsInitializationError,
                    message: String
                ) {
                    Log.e("Unity status", "Initialization  --" + message)
                }
            })

        // store entered gameid in app settings

        // store entered gameid in app settings

        val preferencesEdit = preferences.edit()
        preferencesEdit.putString("gameId", gameId)
        preferencesEdit.commit()

    }

    override fun onResume() {
        super.onResume()
        if (isNetworkConnected()) {
            viewModel?.getHomeData()
        } else {
            showInternetToast()
        }
    }

    override fun likeClick(
        id: String,
        type: String,
        imageView_fav: ImageView,
        imageView_unfav: ImageView
    ) {
        viewModel!!.favUnfavTeacher(getUserToken(), type, id)


    }


    override fun onTokenExpiredListener() {
        viewModel!!.isLoading.postValue(false)

        showTokenError(requireActivity())
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        mmContext = context
    }


    override fun onDetach() {
        super.onDetach()
        UnityBanners.destroy()
        mmContext = null
    }

    override fun onUnityAdsStart(placementId: String?) {

    }

    override fun onUnityAdsFinish(placementId: String?, result: UnityAds.FinishState?) {

    }

    override fun onUnityAdsError(error: UnityAds.UnityAdsError?, message: String?) {

    }

    override fun onUnityAdsReady(zoneId: String?) {
        Utilities.runOnUiThread {

            if (zoneId.equals("Banner_Android")) {
                /*  UnityBanners.setBannerPosition(BannerPosition.BOTTOM_CENTER)
                  UnityBanners.setBannerListener(this)
                  UnityBanners.loadBanner(activity, zoneId)*/
//    UnityBanners.destroy()
                UnityBanners.setBannerPosition(BannerPosition.BOTTOM_CENTER)
                UnityBanners.setBannerListener(this)
                UnityBanners.loadBanner(activityy, "Banner_Android")
            }
            if (zoneId.equals("Interstitial_Android")) {
                interstitialPlacementId = zoneId
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
                interstitialPlacementId = zoneId
                val playerMetaData = PlayerMetaData(activity)
                playerMetaData.setServerId("rikshot")
                playerMetaData.commit()

                val ordinalMetaData = MediationMetaData(activity)
                ordinalMetaData.setOrdinal(ordinal++)
                ordinalMetaData.commit()

                UnityAds.show(activity, interstitialPlacementId)
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
            activity?.addContentView(view, view?.layoutParams)

        }
    }

    override fun onUnityBannerShow(placementId: String?) {

    }

    override fun onUnityBannerClick(placementId: String?) {

    }

    override fun onUnityBannerHide(placementId: String?) {

    }

    override fun onUnityBannerError(message: String?) {
        Log.e("BANNER ERROR", message!!)
    }

    override fun onUnityBannerUnloaded(placementId: String?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        UnityBanners.destroy()
    }
}