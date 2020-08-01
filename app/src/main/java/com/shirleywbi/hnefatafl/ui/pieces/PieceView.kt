package com.shirleywbi.hnefatafl.ui.pieces

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.shirleywbi.hnefatafl.R

open class PieceView: androidx.appcompat.widget.AppCompatImageView {
    private val padding: Int = 8

    constructor(size: Int, context: Context) : this(size, context, null)

    constructor(size: Int, context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setDimensions(size)
        this.setImageResource(R.drawable.board_cell)
        this.setPadding(padding, padding, padding, padding)

        setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                startDrag(v)
                return@setOnTouchListener false
            }
            true
        }
    }

    private fun startDrag(view: View) {
        val item = ClipData.Item(view.tag as CharSequence)
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val dragData = ClipData(view.tag.toString(), mimeTypes, item)
        val shadowBuilder = DragShadowBuilder(view)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(dragData, shadowBuilder, view, 0)
        } else {
            view.startDrag(dragData, shadowBuilder, view, 0)
        }

        // TODO: Leave marker of some sort to indicate original position
        view.visibility = View.INVISIBLE
    }

    private fun setDimensions(size: Int) {
        val params = RelativeLayout.LayoutParams(size, size)
        this.layoutParams = params
    }
}