package com.shirleywbi.hnefatafl.ui.howToPlay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.shirleywbi.hnefatafl.R
import kotlinx.android.synthetic.main.howtoplay.*
import kotlinx.android.synthetic.main.howtoplayfragment.*

class HowToPlayActivity: AppCompatActivity() {

    private val tabCount = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.howtoplay)

        var listener: OnTabSelectedListener = object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                htp_pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        }

        htp_close_btn.setOnClickListener{
            htp_tab_layout.removeOnTabSelectedListener(listener)
            finish()
        }

        for (i: Int in 0 until tabCount) {
            htp_tab_layout.addTab(htp_tab_layout.newTab().setIcon(R.drawable.tab_dot_default))
        }
        htp_pager.adapter = HowToPlayPagerAdapter(supportFragmentManager, htp_tab_layout.tabCount)
        htp_pager.addOnPageChangeListener(TabLayoutOnPageChangeListener(htp_tab_layout))
        htp_tab_layout.addOnTabSelectedListener(listener)
    }

}