package com.shirleywbi.hnefatafl.ui

import android.content.ClipDescription
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import com.shirleywbi.hnefatafl.service.Board
import com.shirleywbi.hnefatafl.service.pieces.Piece
import com.shirleywbi.hnefatafl.service.pieces.PieceType
import com.shirleywbi.hnefatafl.ui.pieces.AttackerPieceView
import com.shirleywbi.hnefatafl.ui.pieces.DefenderPieceView
import com.shirleywbi.hnefatafl.ui.pieces.KingPieceView
import com.shirleywbi.hnefatafl.ui.pieces.PieceView
import com.shirleywbi.hnefatafl.util.getDps

class BoardView : ConstraintLayout {

    private val size: Int = getDps(400, this) / 11

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        addCells()
        addPieces(Board().layoutMap)
        allowDrag(this)
    }

    private fun addCells() {
        for (x in 0..10) {
            for (y in 0..10) {
                val cell: BoardCellView = if ((x == 0 && y == 0) || (x == 10 && y == 0) ||
                    (x == 0 && y == 10) || (x == 10 && y == 10) || (x == 5 && y == 5))
                    BoardRestrictedCellView(size, context) else BoardCellView(size, context)
                cell.tag = "cell-$x-$y"
                cell.x = (x * size).toFloat()
                cell.y = (y * size).toFloat()
                this.addView(cell)
            }
        }
    }

    // TODO: Observer to watch pieces change
    private fun addPieces(layoutMap: HashMap<Pair<Int, Int>, Piece>) {
        var attackerCount = 0
        var defenderCount = 0

        for ((pos, piece) in layoutMap) {
            val pieceView : PieceView =
                when (piece.type) {
                    PieceType.ATTACKER -> AttackerPieceView(size, context)
                    PieceType.DEFENDER -> DefenderPieceView(size, context)
                    PieceType.KING -> KingPieceView(size, context)
                }
            val type = PieceType.ATTACKER

            pieceView.tag = if (type == PieceType.ATTACKER) "attacker-${attackerCount++}" else "defender-${defenderCount++}"
            pieceView.x = (pos.first * size).toFloat()
            pieceView.y = (pos.second * size).toFloat()
            this.addView(pieceView)
        }
    }

    private fun allowDrag(dragDestinationView: View) {
        dragDestinationView.setOnDragListener { view, event ->
            when(event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    Log.i("[DRAG]", "ACTION_DRAG_STARTED")
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        return@setOnDragListener true
                    }
                    return@setOnDragListener false
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    Log.i("[DRAG]", "ACTION_DRAG_ENTERED")
                    return@setOnDragListener true
                }
                DragEvent.ACTION_DRAG_LOCATION -> {
                    Log.i("[DRAG]", "ACTION_DRAG_LOCATION")
                    return@setOnDragListener true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    Log.i("[DRAG]", "ACTION_DRAG_EXITED")
                    return@setOnDragListener true
                }
                DragEvent.ACTION_DROP -> {
                    Log.i("[DRAG]", "ACTION_DROP")
                    val pieceView = event.localState as View
                    val boardOffsetX = this.x + pieceView.marginLeft
                    val boardOffsetY = 0.toFloat()
                    pieceView.x = ((event.x - boardOffsetX)/size).toInt() * size + boardOffsetX
                    pieceView.y = ((event.y - boardOffsetY)/size).toInt() * size + boardOffsetY
                    pieceView.visibility = View.VISIBLE
                    return@setOnDragListener true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    Log.i("[DRAG]", "ACTION_DRAG_ENDED")
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
