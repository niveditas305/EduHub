package com.example.eduhub

 import android.R.attr.fragment
 import android.content.Intent
 import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
 import com.example.eduhub.ui.LearnActivity
 import com.example.eduhub.ui.adapter.FavTutAdapter
 import com.example.eduhub.ui.adapter.HomeTopPickAdapter
 import com.example.eduhub.ui.fragments.*
 import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
 import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity() ,NavigationView.OnNavigationItemSelectedListener {


lateinit var  homeTopPickAdapter:HomeTopPickAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


         setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,  R.string.navigation_drawer_open,  R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.getNavigationIcon()?.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        nav_view.setNavigationItemSelectedListener(this)
        loadFragment(HomeFragment())
    }



    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.home -> {

                tv_heading.setText("EDUHUB SOUTH AFRICA")
                loadFragment(HomeFragment())

            } R.id.my_profile -> {
             startActivity(Intent(applicationContext, LearnActivity::class.java))
            }
            R.id.learn -> {

                tv_heading.setText("Learn")
                loadFragment(LearnFragment())

            }
            R.id.test -> {

                tv_heading.setText("Test")
                loadFragment(TestFragment())

            }
            R.id.solution -> {


                tv_heading.setText("Test")
                loadFragment(TestFragment())

               // startActivity(Intent(applicationContext, WithdrawScreen::class.java))
            }

            R.id.score -> {


                tv_heading.setText("Score")
                loadFragment(TestScoreFragment())
             //   startActivity(Intent(applicationContext, SettingsActivity::class.java))

            }
            R.id.favtutor -> {

                tv_heading.setText("Favourite Tutor")
                loadFragment(FavouriteTutorFragment())
              //  startActivity(Intent(applicationContext, SettingsActivity::class.java))

            }
            R.id.chat -> {
          //      startActivity(Intent(applicationContext, SettingsActivity::class.java))

                tv_heading.setText(getString(R.string.feedback))
                loadFragment(ChatFragment())
            }
            R.id.setting -> {
            //    startActivity(Intent(applicationContext, SettingsActivity::class.java))
                tv_heading.setText(getString(R.string.setting))
                loadFragment(SettingFragment())
            }

            R.id.logout -> {

            }



        }


        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun loadFragment(fragment: Fragment) { // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
    //  transaction.addToBackStack(null);
        transaction.commit()
    }



}