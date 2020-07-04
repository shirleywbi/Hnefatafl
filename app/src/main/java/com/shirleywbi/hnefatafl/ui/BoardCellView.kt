package com.shirleywbi.hnefatafl.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.shirleywbi.hnefatafl.R

open class BoardCellView : androidx.appcompat.widget.AppCompatImageView {

    constructor(size: Int, context: Context) : this(size, context, null)

    constructor(size: Int, context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setDimensions(size)
        this.setImageResource(R.drawable.board_cell)
    }

    fun setDimensions(size: Int) {
        val params = RelativeLayout.LayoutParams(size, size)
        this.layoutParams = params
    }

}