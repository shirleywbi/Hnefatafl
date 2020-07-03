package com.shirleywbi.hnefatafl.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.shirleywbi.hnefatafl.util.getDps

class BoardCellView : View {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        val length: Int = getDps(50, this)
        this.layoutParams = ViewGroup.LayoutParams(length, length)
        this.setBackgroundColor(Color.BLACK)
    }
}