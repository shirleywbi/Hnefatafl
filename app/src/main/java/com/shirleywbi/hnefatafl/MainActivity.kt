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

        more_info_btn.setOnClickListener {
            startActivity(Intent(this, HowToPlayActivity::class.java))
        }

        game = intent.getSerializableExtra("GAME") as Game
        gameBoard = game.gameBoard

        board_layout.addPieces(gameBoard.layoutMap)
        allowDrag(board_layout)
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
                            val capturePositions: List<Pair<Int, Int>> = it.getCapturedPositions(newPiecePos.first, newPiecePos.second, gameBoard.layoutMap, game.piece)
                            capturePositions.forEach{ pos ->
                                var captured: PieceView = board_layout.findViewWithTag(gameBoard.layoutMap[pos]?.label)
                                board_layout.removeView(captured)
                            }
                        }
                    }
                    pieceView.visibility = View.VISIBLE
                    return@setOnDragListener true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    if (!event.result) {
                        val draggableView = event.localState as View
                        draggableView.visibility = View.VISIBLE
                    }
                }
            }

            false
        }
    }


}