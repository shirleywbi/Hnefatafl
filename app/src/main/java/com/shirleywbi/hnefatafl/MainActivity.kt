package com.shirleywbi.hnefatafl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.shirleywbi.hnefatafl.service.Game
import com.shirleywbi.hnefatafl.service.pieces.PieceType
import com.shirleywbi.hnefatafl.ui.howToPlay.HowToPlayActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        more_info_btn.setOnClickListener {
            startActivity(Intent(this, HowToPlayActivity::class.java))
        }
    }

    // For pieces
    fun setListeners() {
//        val views = List<View> =
//            listOf(view1, view2, etc.)
//
//        for (view in views) {
//            view.setOnClickListener { dragAndDrop(it) }
//        }
    }

    fun dragAndDrop(view: View) {
        when (view.id) {
            // Do something
        }
    }

}