package com.shirleywbi.hnefatafl.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
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
//        addPieces() // TODO: Load layout map
    }

    private fun addCells() {
        for (x in 0..10) {
            for (y in 0..10) {
                var cell: BoardCellView = if ((x == 0 && y == 0) || (x == 10 && y == 0) ||
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
            var pieceView : PieceView =
                when (piece.type) {
                    PieceType.ATTACKER -> AttackerPieceView(size, context)
                    PieceType.DEFENDER -> DefenderPieceView(size, context)
                    PieceType.KING -> KingPieceView(size, context)
                }
            var type = PieceType.ATTACKER

            pieceView.tag = if (type == PieceType.ATTACKER) "attacker-${attackerCount++}" else "defender-${defenderCount++}"
            pieceView.x = (pos.first * size).toFloat()
            pieceView.y = (pos.second * size).toFloat()
            this.addView(pieceView)
        }
    }

}
