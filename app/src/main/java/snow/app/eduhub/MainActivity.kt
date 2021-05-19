package snow.app.eduhub

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.ads.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import snow.app.eduhub.repo.OnDataReadyCallback
import snow.app.eduhub.repo.Repo
import snow.app.eduhub.ui.NotificationScreen
import snow.app.eduhub.ui.ProfileFragment
import snow.app.eduhub.ui.adapter.HomeTopPickAdapter
import snow.app.eduhub.ui.fragments.*
import snow.app.eduhub.ui.network.responses.NotificationCountRes
import snow.app.eduhub.util.BaseActivity


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var tv_name: TextView
    lateinit var tv_schoolname: TextView
    lateinit var tv_grade: TextView
    lateinit var tv_unread_noti: TextView
    lateinit var iv_profile: ImageView
    lateinit var iv_facebook: ImageView
    lateinit var iv_insta: ImageView
    lateinit var toolbar: Toolbar
    lateinit var iv_youtube: ImageView
    lateinit var iv_noti: ImageView
    private lateinit var mInterstitialAd: InterstitialAd
    var repoModel: Repo = Repo()
    lateinit var homeTopPickAdapter: HomeTopPickAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
          toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        iv_noti = toolbar.findViewById<ImageView>(R.id.iv_noti)
        tv_unread_noti = toolbar.findViewById<TextView>(R.id.tv_unread_noti)

         getSupportActionBar()?.setDisplayShowTitleEnabled(false);

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.getNavigationIcon()?.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        nav_view.setNavigationItemSelectedListener(this)

        val v: View = nav_view.getHeaderView(0)

        nav_view.getMenu().findItem(R.id.nav_home).actionView.findViewById<ImageView>(R.id.iv_insta)
            .setOnClickListener {
                gotoLink("https://www.instagram.com/invites/contact/?i=1kv8gyrk5s6vu&utm_content=kkypow8")
            }


        nav_view.getMenu()
            .findItem(R.id.nav_home).actionView.findViewById<ImageView>(R.id.iv_facebook)
            .setOnClickListener {
                gotoLink("https://www.facebook.com/EduHubAfrica")
            }
        nav_view.getMenu()
            .findItem(R.id.nav_home).actionView.findViewById<ImageView>(R.id.iv_youtube)
            .setOnClickListener {
                gotoLink("https://www.youtube.com/")
            }


        iv_profile = v.findViewById<ImageView>(R.id.iv_profile)


        //   iv_youtube = v.findViewById<ImageView>(R.id.iv_youtube)
        //  iv_facebook = v.findViewById<ImageView>(R.id.iv_facebook)
        tv_name = v.findViewById<TextView>(R.id.tv_name)
        tv_schoolname = v.findViewById<TextView>(R.id.tv_schoolname)
        tv_grade = v.findViewById<TextView>(R.id.tv_grade)


        tv_name.setOnClickListener {
            loadFragment(ProfileFragment(), "")
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        iv_noti.setOnClickListener {
            startActivity(Intent(this, NotificationScreen::class.java))
        }




        if (getSession()?.getAppData()?.data?.schoolClassName!!.isEmpty()) {
            tv_heading.setText("Profile")
            loadFragment(ProfileFragment(), "")
        } else {
            if (intent.hasExtra("chat")) {
                loadFragment(ChatFragmentJava(), "")
            } else {
                loadFragment(HomeFragment(), "Home")
            }
        }

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

    override fun onBackPressed() {


        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            //  super.onBackPressed()
            if (getSession()?.getAppData()?.data?.schoolClassName!!.isEmpty()) {
                super.onBackPressed()
            } else {

                val fragment: Fragment? = supportFragmentManager.findFragmentByTag("Home")

                Log.e("frag", "--" + fragment)
                if (fragment != null && fragment is HomeFragment) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Exit")
                    builder.setMessage("Are you sure you want to exit the app?")
                    builder.setPositiveButton(
                        "Exit"
                    ) { dialog: DialogInterface?, which: Int ->


                        super.onBackPressed()


                    }

                    builder.setNegativeButton(
                        "cancel"
                    ) { dialog: DialogInterface?, which: Int ->


                        dialog?.dismiss()
                    }
                    builder.show()
                } else {
                    tv_heading.setText("EDUHUB SOUTH AFRICA")
                    loadFragment(HomeFragment(), "Home")
                }

            }

        }
    }

    override fun onResume() {
        super.onResume()
        // side drawer data
        if (getSession()?.getAppData()?.data?.schoolClassName != null) {
            tv_grade.setText("Grade: " + getSession()?.getAppData()?.data?.schoolClassName)
        }
        if (getSession()?.getAppData()?.data?.studentSchool != null) {
            tv_schoolname.setText(getSession()?.getAppData()?.data?.studentSchool)
        }

        tv_name.setText(getSession()?.getAppData()?.data?.firstName + " " + getSession()?.getAppData()?.data?.lastName)


        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.user)
            .error(R.drawable.user)


        if (getSession()?.getAppData()?.data?.studentImage != null) {
            Glide.with(this).load(getSession()?.getAppData()?.data?.studentImage).apply(options)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {


            R.id.iv_facebook -> {
                showToast("hello fb")
            }
            R.id.home -> {

                tv_heading.setText("EDUHUB SOUTH AFRICA")
                loadFragment(HomeFragment(), "Home")

            }
            R.id.my_profile -> {

                tv_heading.setText("Profile")
                loadFragment(ProfileFragment(), "")
            }
            R.id.learn -> {

                tv_heading.setText("Lesson")
                loadFragment(LearnFragment("lesson"), "")

            }
            R.id.test -> {

                tv_heading.setText("Test")
                loadFragment(TestSubjectFragment(), "")

            }


            R.id.worksheets -> {

                tv_heading.setText("Worksheets")
                loadFragment(LearnFragment("work"), "")

            }
            /*  R.id.solution -> {


                  tv_heading.setText("Test")
                  loadFragment(TestFragment(), "")

                  // startActivity(Intent(applicationContext, WithdrawScreen::class.java))
              }*/

            R.id.score -> {


                tv_heading.setText("Score")
                loadFragment(TestScoreFragment(), "")
                //   startActivity(Intent(applicationContext, SettingsActivity::class.java))

            }
            R.id.question_bank -> {


                tv_heading.setText("Past Question Papers")
                loadFragment(PastSubjectFragment(), "")
                //   startActivity(Intent(applicationContext, SettingsActivity::class.java))

            }
            R.id.favtutor -> {

                tv_heading.setText("Favourite Teachers")
                loadFragment(FavouriteTutorFragment(), "")
                //  startActivity(Intent(applicationContext, SettingsActivity::class.java))

            }
            R.id.chat -> {
                //      startActivity(Intent(applicationContext, SettingsActivity::class.java))

                tv_heading.setText(getString(R.string.feedback))
                loadFragment(ChatFragmentJava(), "")
            }
            R.id.setting -> {
                //    startActivity(Intent(applicationContext, SettingsActivity::class.java))
                tv_heading.setText(getString(R.string.setting))
                loadFragment(SettingFragment(), "")
            }

            R.id.logout -> {


                dialog?.show()
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Logout")
                builder.setMessage("Are you sure you want to logout?")
                builder.setPositiveButton(
                    "Logout"
                ) { dialogg: DialogInterface?, which: Int ->
                    dialogg?.dismiss()
                    getSession()?.logout()
                    dialog?.dismiss()
                  //  viewModel!!.logout()


                }

                builder.setNegativeButton(
                    "cancel"
                ) { dialogg: DialogInterface?, which: Int ->
                    dialogg?.dismiss()

                    dialog?.dismiss()
                }
                builder.show()

            }


        }


        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    val onDataReadyCallback = object : OnDataReadyCallback {
        override fun onDataReady(data: Any?, isErrr: Boolean) {
            // isLoading.postValue(false)
            Log.e("callback", "callback");
            if (data != null) {
                val d = data as NotificationCountRes?
                if (d?.status!!) {
                    // respData.postValue(d)
                    //  val textView = notificationBadge?.findViewById<TextView>(R.id.counter_badge)
                    //   itemView.addView(notificationBadge)
                    if (d.count == 0) {
                        /*    textView?.text =""
                            textView?.visibility=View.GONE*/
                        //  getBadge()
                        //  removeBadge()

                        tv_unread_noti.visibility = View.GONE

                    } else {

                        tv_unread_noti.visibility = View.VISIBLE

                        tv_unread_noti.setText(d.count.toString())
                        /*textView?.text =d.data.toString()
                       textView?.visibility=View.VISIBLE*/

                        //  addBadge(d.count.toString())

                    }
                    //  itemView.addView(notificationBadge)
                } else {
                    // isError.postValue(AlertModel("Something went wrong",true));
                }


            } else {
                //  isError.postValue(AlertModel("Something went wrong.",true));
            }

        }
    }


    //update interval for widget
    val UPDATE_INTERVAL = 5000L

    //Handler to repeat update
    private val updateWidgetHandler = Handler()
    private var updateWidgetRunnable: Runnable = Runnable {
        run {
            //Update UI

            fetchNotificationDetails()
            // Re-run it after the update interval
            updateWidgetHandler.postDelayed(updateWidgetRunnable, UPDATE_INTERVAL)
        }
    }

    fun fetchNotificationDetails() {
        // isLoading.postValue(true)

        repoModel.getNotificationsCount(onDataReadyCallback, getUserToken())
    }

    private fun loadFragment(fragment: Fragment, tag: String) { // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment, tag)
        //     transaction.addToBackStack(null);
        transaction.commit()
    }

    override fun onStart() {
        super.onStart()
        updateWidgetHandler.postDelayed(updateWidgetRunnable, UPDATE_INTERVAL)
    }

    override fun onPause() {
        super.onPause()
        updateWidgetHandler.removeCallbacks(updateWidgetRunnable)
    }

    fun gotoLink(link: String) {
        val viewIntent = Intent(
            "android.intent.action.VIEW",
            Uri.parse(link)
        )
        startActivity(viewIntent)
    }
}