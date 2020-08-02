package com.shirleywbi.hnefatafl.ui

import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import com.shirleywbi.hnefatafl.service.pieces.Piece
import com.shirleywbi.hnefatafl.service.pieces.PieceType
import com.shirleywbi.hnefatafl.ui.pieces.AttackerPieceView
import com.shirleywbi.hnefatafl.ui.pieces.DefenderPieceView
import com.shirleywbi.hnefatafl.ui.pieces.KingPieceView
import com.shirleywbi.hnefatafl.ui.pieces.PieceView
import com.shirleywbi.hnefatafl.util.getDps

class BoardView : ConstraintLayout {

    var size = getDps(400, this) / 11

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        addCells()
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
    fun addPieces(layoutMap: HashMap<Pair<Int, Int>, Piece>) {
        for ((pos, piece) in layoutMap) {
            val pieceView : PieceView =
                when (piece.type) {
                    PieceType.ATTACKER -> AttackerPieceView(size, context)
                    PieceType.DEFENDER -> DefenderPieceView(size, context)
                    PieceType.KING -> KingPieceView(size, context)
                }
            pieceView.tag = piece.label
            pieceView.x = (pos.first * size).toFloat()
            pieceView.y = (pos.second * size).toFloat()
            this.addView(pieceView)
        }
    }

    fun calculatePieceBoardPosition(pieceView: View, event: DragEvent): Pair<Int, Int> {
        val boardOffsetX = this.x + pieceView.marginLeft
        val boardOffsetY = 0.toFloat()
        val newPiecePosX = ((event.x - boardOffsetX)/this.size).toInt()
        val newPiecePosY = ((event.y - boardOffsetY)/this.size).toInt()
        return Pair(newPiecePosX, newPiecePosY)
    }
}
