package com.shirleywbi.hnefatafl.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet

class BoardRestrictedCellView : BoardCellView {

    constructor(size: Int, context: Context) : this(size, context, null)

    constructor(size: Int, context: Context, attrs: AttributeSet?) : super(size, context, attrs) {
        super.setDimensions(size)
        this.setBackgroundColor(Color.BLUE)
    }
}