package com.shirleywbi.hnefatafl.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.shirleywbi.hnefatafl.util.getDps

class BoardView : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        var cells: ArrayList<BoardCellView> = arrayListOf()

        // TODO: Get actual width
        // TODO: DPS is uneven, may need absolute but flexible board as a whole
        val size: Int = getDps(250, this) / 11

        for (x in 0..10) {
            for (y in 0..10) {
                var cell: BoardCellView = if ((x == 0 && y == 0) || (x == 10 && y == 0) ||
                    (x == 0 && y == 10) || (x == 10 && y == 10) || (x == 5 && y == 5))
                    BoardRestrictedCellView(size, context) else BoardCellView(size, context)
                cell.tag = "cell-$x-$y"
                cell.x = (x * size).toFloat()
                cell.y = (y * size).toFloat()
                cells.add(cell)
            }
        }

        cells.forEach { cell -> this.addView(cell)}

    }
}
