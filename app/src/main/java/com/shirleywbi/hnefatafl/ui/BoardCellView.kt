package com.shirleywbi.hnefatafl.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

open class BoardCellView : View {

    constructor(size: Int, context: Context) : this(size, context, null)

    constructor(size: Int, context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setDimensions(size)
        this.setBackgroundColor(Color.BLACK)
    }

    fun setDimensions(size: Int) {
        val params = RelativeLayout.LayoutParams(size, size)
        this.layoutParams = params
    }
}