package com.gemastik.ideation.ui.PmDetailSide.contributorAcc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.gemastik.ideation.R
import com.gemastik.ideation.databinding.ActivityContributorAccBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ContributorAccActivity : AppCompatActivity() {

    private lateinit var binding : ActivityContributorAccBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContributorAccBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val contributorSectionPager = ContributorSectionPager(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = contributorSectionPager
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }


    private fun showData(){


    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_cont_1,
            R.string.tab_cont_2
        )
    }
}