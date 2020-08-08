package com.shirleywbi.hnefatafl

import android.content.ClipDescription
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import androidx.core.view.marginLeft
import com.shirleywbi.hnefatafl.service.Board
import com.shirleywbi.hnefatafl.service.Game
import com.shirleywbi.hnefatafl.service.GameOverStatus
import com.shirleywbi.hnefatafl.service.pieces.Piece
import com.shirleywbi.hnefatafl.ui.GameOverFragment
import com.shirleywbi.hnefatafl.ui.GameStartActivity
import com.shirleywbi.hnefatafl.ui.howToPlay.HowToPlayActivity
import com.shirleywbi.hnefatafl.ui.pieces.PieceView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var game: Game
    private lateinit var gameBoard: Board
    private var selectedPiecePos: Pair<Int,Int> = Pair(-1, -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        game = intent.getSerializableExtra("GAME") as Game
        gameBoard = game.gameBoard

        setListeners()
        board_layout.addPieces(gameBoard.layoutMap)
        allowDrag(board_layout)
    }

    private fun setListeners() {
        more_info_btn.setOnClickListener {
            startActivity(Intent(this, HowToPlayActivity::class.java))
        }
        forfeit_btn.setOnClickListener {
            startActivity(Intent(this, GameStartActivity::class.java))
        }
        undo_btn.setOnClickListener {
            /**To be implemented:
             * - Revert gameBoard.boardHistory by one
             * - Update board view
             * */
        }
    }

    private fun allowDrag(dragDestinationView: View) {
        dragDestinationView.setOnDragListener { view, event ->
            when(event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        val pieceView = event.localState as View
                        selectedPiecePos = board_layout.calculatePieceBoardPosition(pieceView, event)
                        return@setOnDragListener true
                    }
                    return@setOnDragListener false
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    return@setOnDragListener true
                }
                DragEvent.ACTION_DRAG_LOCATION -> {
                    return@setOnDragListener true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    return@setOnDragListener true
                }
                DragEvent.ACTION_DROP -> {
                    val pieceView = event.localState as View
                    val boardOffsetX = board_layout.x + pieceView.marginLeft
                    val boardOffsetY = 0.toFloat()
                    val newPiecePos = board_layout.calculatePieceBoardPosition(pieceView, event)

                    if (gameBoard.canMove(gameBoard.layoutMap[selectedPiecePos], newPiecePos.first, newPiecePos.second)) {
                        pieceView.x = newPiecePos.first * board_layout.size + boardOffsetX
                        pieceView.y = newPiecePos.second * board_layout.size + boardOffsetY
                        gameBoard.layoutMap[selectedPiecePos]?.let {
                            gameBoard.move(it, newPiecePos.first, newPiecePos.second)
                            val capturedPositionsAndPieces: HashMap<Pair<Int, Int>, Piece> = it.capturePositionsAndPieces(newPiecePos.first, newPiecePos.second, gameBoard.layoutMap)
                            capturedPositionsAndPieces.forEach{ (pos, piece) ->
                                gameBoard.checkAttackerWin(piece)
                                var captured: PieceView = board_layout.findViewWithTag(capturedPositionsAndPieces[pos]?.label)
                                board_layout.removeView(captured)
                            }
                        }
                        player_turn_text.text = getString(if (gameBoard.isAttackerTurn) R.string.attacker else R.string.defender)
                    }
                    pieceView.visibility = View.VISIBLE
                    return@setOnDragListener true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    if (!event.result) {
                        val draggableView = event.localState as View
                        draggableView.visibility = View.VISIBLE
                    }
                    if (gameBoard.isGameOver) {
                        showGameOverDialog(gameBoard.gameOverStatus)
                    }
                }
            }

            false
        }
    }

    private fun showGameOverDialog(status: GameOverStatus) {
        val dialog = GameOverFragment(status)
        dialog.show(supportFragmentManager, "GameOverFragment")
    }

}