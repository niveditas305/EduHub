package snow.app.eduhub.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import snow.app.eduhub.ui.fragments.*


class TestPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return MathemathicsTestFragmnet()
            1 -> return ScienceTestFragment()


        }
        return MathemathicsTestFragmnet()
    }



    override fun getCount(): Int {
        return 2
    }



}