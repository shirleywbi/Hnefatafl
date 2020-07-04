package com.shirleywbi.hnefatafl.ui.pieces

import android.content.Context
import android.util.AttributeSet
import com.shirleywbi.hnefatafl.R

class KingPieceView: DefenderPieceView {

    constructor(size: Int, context: Context) : this(size, context, null)

    constructor(size: Int, context: Context, attrs: AttributeSet?) : super(size, context, attrs) {
        this.setImageResource(R.drawable.piece_defender)
        // TODO: Differentiate king from defender
    }

}