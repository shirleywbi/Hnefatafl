package com.shirleywbi.hnefatafl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shirleywbi.hnefatafl.service.Game
import com.shirleywbi.hnefatafl.service.pieces.PlayerType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPlayerSelectListeners()
    }

    fun setPlayerSelectListeners() {
        attacker_btn.setOnClickListener {
            game.selectPlayer(PlayerType.ATTACKER)
        }
        defender_btn.setOnClickListener {
            game.selectPlayer(PlayerType.DEFENDER)
        }
    }

}