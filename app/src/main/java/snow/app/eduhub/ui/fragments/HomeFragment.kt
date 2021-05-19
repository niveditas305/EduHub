package snow.app.eduhub.ui.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
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
    OnTokenExpired.OnTokenExpiredListener {
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
    var list: ArrayList<TopTeacher> =
        ArrayList<TopTeacher>()
    lateinit var tv_one: LinearLayout
    var topteacherlist: ArrayList<TopTeacher> = ArrayList()
    var toptopicslist: ArrayList<TopTopic> = ArrayList()
    var subjectlist: ArrayList<Subject> = ArrayList()
    var bannersList: ArrayList<Banner> = ArrayList()
    var viewModel: HomeViewModel? = null
    lateinit var binding: FragmentHome2Binding
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
        mmContext = null
    }
}