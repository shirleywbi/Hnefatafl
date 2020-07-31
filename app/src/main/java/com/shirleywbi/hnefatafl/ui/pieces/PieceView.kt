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

    private fun allowDrag(dragDestinationView: View) {
        val originalBackground = dragDestinationView.background
        dragDestinationView.setOnDragListener { view, event ->
        when(event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    return@setOnDragListener true
                }
                return@setOnDragListener false
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.setBackgroundResource(R.color.boardDarkColor)
                view.invalidate() // redraw itself
                return@setOnDragListener true
            }
            DragEvent.ACTION_DRAG_LOCATION -> return@setOnDragListener true
            DragEvent.ACTION_DRAG_EXITED -> {
                // undo whatever you did in Drag Started & entered
                view.background = originalBackground
                view.invalidate()
                return@setOnDragListener true
            }
            DragEvent.ACTION_DROP -> {
                val clipDataItem = event.clipData.getItemAt(0)
                val dragData = clipDataItem.text.toString()

                view.background = originalBackground
                view.invalidate()

                val draggabbleView = event.localState as View
                with(draggabbleView) {
                    val owner = parent as ViewGroup
                    if (owner != dragDestinationView) {
                        owner.removeView(this)
                        val container = view as FrameLayout
                        container.addView(draggabbleView)
                        draggabbleView.visibility = View.VISIBLE
                        return@setOnDragListener true
                    }
                }
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                view.background = originalBackground
                view.invalidate()

                if (event.result) {
                    // drag worked
                } else {
                    // drag didnt work
                    val draggabbleView = event.localState as View
                    draggabbleView.visibility = View.VISIBLE

                }
            }
        }

        false
        }
    }

    private fun setDimensions(size: Int) {
        val params = RelativeLayout.LayoutParams(size, size)
        this.layoutParams = params
    }
}