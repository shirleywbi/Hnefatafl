package com.shirleywbi.hnefatafl.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shirleywbi.hnefatafl.MainActivity
import com.shirleywbi.hnefatafl.R
import com.shirleywbi.hnefatafl.service.Game
import com.shirleywbi.hnefatafl.service.pieces.PieceType
import com.shirleywbi.hnefatafl.ui.howToPlay.HowToPlayActivity
import kotlinx.android.synthetic.main.gamestart_layout.*
import kotlinx.android.synthetic.main.gamestart_layout.more_info_btn

class GameStartActivity: AppCompatActivity() {

    private var game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gamestart_layout)

        setPlayerSelectListeners()

        more_info_btn.setOnClickListener {
            startActivity(Intent(this, HowToPlayActivity::class.java))
        }
    }

    private fun setPlayerSelectListeners() {
        attacker_btn.setOnClickListener {
            game.selectPlayer(PieceType.ATTACKER)
            startActivity(Intent(this, MainActivity::class.java))
        }
        defender_btn.setOnClickListener {
            game.selectPlayer(PieceType.DEFENDER)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}