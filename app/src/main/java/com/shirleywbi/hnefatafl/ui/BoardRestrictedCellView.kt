package com.shirleywbi.hnefatafl.ui

import android.content.Context
import android.util.AttributeSet
import com.shirleywbi.hnefatafl.R

class BoardRestrictedCellView : BoardCellView {

    constructor(size: Int, context: Context) : this(size, context, null)

    constructor(size: Int, context: Context, attrs: AttributeSet?) : super(size, context, attrs) {
        super.setDimensions(size)
        this.setImageResource(R.drawable.board_restricted_cell)
    }
}