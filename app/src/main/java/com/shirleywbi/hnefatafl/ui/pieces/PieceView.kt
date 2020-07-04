package com.shirleywbi.hnefatafl.ui.pieces

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.shirleywbi.hnefatafl.R

open class PieceView: androidx.appcompat.widget.AppCompatImageView {
    private val padding: Int = 8

    constructor(size: Int, context: Context) : this(size, context, null)

    constructor(size: Int, context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setDimensions(size)
        this.setImageResource(R.drawable.board_cell)
        this.setPadding(padding, padding, padding, padding)
    }

    private fun setDimensions(size: Int) {
        val params = RelativeLayout.LayoutParams(size, size)
        this.layoutParams = params
    }
}