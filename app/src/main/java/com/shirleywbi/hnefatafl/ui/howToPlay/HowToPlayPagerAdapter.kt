package com.shirleywbi.hnefatafl.ui.howToPlay

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.shirleywbi.hnefatafl.R

class HowToPlayPagerAdapter: FragmentStatePagerAdapter {

    private var numTabs: Int

    constructor(fm: FragmentManager, numTabs: Int) : super(fm) {
        this.numTabs = numTabs
    }

    override fun getItem(position: Int): HowToPlayFragment {
        return when (position) {
            0 -> HowToPlayFragment(R.string.how_to_play, R.string.htp_goal_details, listOf(R.drawable.viking_default))
            1 -> HowToPlayFragment(R.string.htp_your_turn, R.string.htp_your_turn_details)
            2 -> HowToPlayFragment(R.string.htp_capture, R.string.htp_capture_details)
            3 -> HowToPlayFragment(R.string.htp_defender_win, R.string.htp_defender_win_details)
            4 -> HowToPlayFragment(R.string.htp_attacker_win, R.string.htp_attacker_win_details)
            else -> HowToPlayFragment(R.string.htp_game_over, R.string.htp_game_over_details)
        }
    }

    override fun getCount(): Int {
        return this.numTabs
    }

}