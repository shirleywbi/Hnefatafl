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

    var game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPlayerSelectListeners()

        more_info_btn.setOnClickListener {
            startActivity(Intent(this, HowToPlayActivity::class.java))
        }
    }

    fun setPlayerSelectListeners() {
        attacker_btn.setOnClickListener {
            game.selectPlayer(PieceType.ATTACKER)
            hidePlayerSelectButtons()
        }
        defender_btn.setOnClickListener {
            game.selectPlayer(PieceType.DEFENDER)
            hidePlayerSelectButtons()
        }
    }

    fun hidePlayerSelectButtons() {
        choose_player_text.visibility = View.GONE
        attacker_btn.visibility = View.GONE
        defender_btn.visibility = View.GONE
    }

}