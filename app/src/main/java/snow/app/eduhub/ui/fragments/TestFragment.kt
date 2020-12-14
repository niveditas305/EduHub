package snow.app.eduhub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import snow.app.eduhub.R
import snow.app.eduhub.ui.adapter.TestPagerAdapter
import com.google.android.material.tabs.TabLayout

class TestFragment : Fragment() {
lateinit var  tab_test:TabLayout
lateinit var  vp_test:ViewPager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_test, container, false)

        tab_test=view.findViewById(R.id.tab_test)
        vp_test=view.findViewById(R.id.vp_test)
        tab_test.addTab(tab_test.newTab().setText( getString(R.string.mathematics)))
        tab_test.addTab(tab_test.newTab().setText(  getString(R.string.science)))
        tab_test.tabGravity = TabLayout.GRAVITY_FILL

        val testPagerAdapter = TestPagerAdapter(parentFragmentManager)
        vp_test.adapter = testPagerAdapter

        vp_test.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_test))
        tab_test.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                vp_test.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        return  view
    }


}