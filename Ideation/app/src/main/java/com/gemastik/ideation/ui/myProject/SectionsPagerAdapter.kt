package com.gemastik.ideation.ui.myProject

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {


    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = OngoingFragment()
            1 -> fragment = FinishFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}