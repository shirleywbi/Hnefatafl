package com.shirleywbi.hnefatafl.ui.howToPlay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.shirleywbi.hnefatafl.R
import kotlinx.android.synthetic.main.howtoplay.*

class HowToPlayActivity: AppCompatActivity() {

    private val tabCount = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.howtoplay)

        // Add tab listener
        var listener: OnTabSelectedListener = object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                htp_pager.currentItem = tab.position
                htp_tab_layout.getTabAt(tab.position)?.setIcon(R.drawable.tab_dot_selected)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                htp_tab_layout.getTabAt(tab.position)?.setIcon(R.drawable.tab_dot_default)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        }

        htp_close_btn.setOnClickListener{
            htp_tab_layout.removeOnTabSelectedListener(listener)
            finish()
        }

        // Add tabs
        for (i: Int in 0 until tabCount) {
            htp_tab_layout.addTab(htp_tab_layout.newTab().setIcon(R.drawable.tab_dot_default))
        }

        // Handle page changing
        htp_pager.adapter = HowToPlayPagerAdapter(supportFragmentManager, htp_tab_layout.tabCount)
        htp_pager.addOnPageChangeListener(TabLayoutOnPageChangeListener(htp_tab_layout))
        htp_tab_layout.addOnTabSelectedListener(listener)
        // TODO: Figure out why this line is not working
        htp_tab_layout.getTabAt(0)?.select()
    }

}