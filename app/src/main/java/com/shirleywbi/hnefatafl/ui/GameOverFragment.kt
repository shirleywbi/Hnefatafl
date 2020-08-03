package com.shirleywbi.hnefatafl.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.shirleywbi.hnefatafl.R
import com.shirleywbi.hnefatafl.service.GameOverStatus

class GameOverFragment(private val status: GameOverStatus): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var gameOverTitle = when(status) {
            GameOverStatus.WIN -> R.string.game_over_win
            GameOverStatus.LOSE -> R.string.game_over_lose
            GameOverStatus.DRAW -> R.string.game_over_draw
        }
        var gameOverDescription = when(status) {
            GameOverStatus.WIN -> R.string.game_over_win_description
            GameOverStatus.LOSE -> R.string.game_over_lose_description
            GameOverStatus.DRAW -> R.string.game_over_draw_description
        }
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(gameOverTitle)
                .setMessage(gameOverDescription)
                .setPositiveButton(R.string.new_game,
                    DialogInterface.OnClickListener { dialog, id ->
                        startActivity(Intent(context, GameStartActivity::class.java))
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}