package com.shirleywbi.hnefatafl.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

class BoardView : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        var cells: ArrayList<BoardCellView> = arrayListOf()

        for (x in 0..10) {
            for (y in 0..10) {
                val cell = BoardCellView(context)
                cell.tag = "$x-$y"
                cells.add(BoardCellView(context))
            }
        }

        cells.forEach { cell -> this.addView(cell)}
    }
}