package com.shirleywbi.hnefatafl.ui.pages

import android.app.Activity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import com.shirleywbi.hnefatafl.R
import kotlinx.android.synthetic.main.howtoplay.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class HowToPlayActivity: Activity(), GestureDetector.OnGestureListener {

    private val SWIPE_THRESHOLD = 100
    private val SWIPE_VELOCITY_THRESHOLD = 100
    private var index = 0
    private var gameGuideText: ArrayList<Pair<String, String>> = arrayListOf()
    private lateinit var mDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.howtoplay)

        htp_close_btn.setOnClickListener{
            finish()
        }

        loadGameGuide()
        htp_title.text = gameGuideText[index].first
        htp_text.text = gameGuideText[index].second

        mDetector = GestureDetectorCompat(this, this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    private fun loadGameGuide() {
        gameGuideText.add(Pair(resources.getString(R.string.how_to_play), resources.getString(R.string.htp_goal_details)))
        gameGuideText.add(Pair(resources.getString(R.string.htp_your_turn), resources.getString(R.string.htp_your_turn_details)))
        gameGuideText.add(Pair(resources.getString(R.string.htp_capture), resources.getString(R.string.htp_capture_details)))
        gameGuideText.add(Pair(resources.getString(R.string.htp_defender_win), resources.getString(R.string.htp_defender_win_details)))
        gameGuideText.add(Pair(resources.getString(R.string.htp_attacker_win), resources.getString(R.string.htp_attacker_win_details)))
        gameGuideText.add(Pair(resources.getString(R.string.htp_game_over), resources.getString(R.string.htp_game_over_details)))
    }

    override fun onFling(downEvent: MotionEvent, moveEvent: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        var result = false
        var diffY = moveEvent.y - downEvent.y
        var diffX = moveEvent.x - downEvent.x
        if (abs(diffX) > abs(diffY)) {
            if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }
                result = true
            }
        }
        return result
    }

    private fun onSwipeRight() {
        index = min(index + 1, gameGuideText.size - 1)
        htp_title.text = gameGuideText[index].first
        htp_text.text = gameGuideText[index].second
    }

    private fun onSwipeLeft() {
        index = max(0, index - 1)
        htp_title.text = gameGuideText[index].first
        htp_text.text = gameGuideText[index].second
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {}


    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {}
}