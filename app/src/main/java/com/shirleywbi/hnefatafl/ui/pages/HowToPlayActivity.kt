package com.shirleywbi.hnefatafl.ui.pages

import android.app.Activity
import android.os.Bundle
import com.shirleywbi.hnefatafl.R
import kotlinx.android.synthetic.main.howtoplay.*

class HowToPlayActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.howtoplay)

        htp_close_btn.setOnClickListener{
            finish()
        }
    }
}