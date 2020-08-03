package com.shirleywbi.hnefatafl.util

import android.view.View
import com.shirleywbi.hnefatafl.service.pieces.Piece
import com.shirleywbi.hnefatafl.service.pieces.PieceType

fun getDps(dps: Int, view: View): Int {
    val scale = view.context.resources.displayMetrics.density
    return (dps * scale + 0.5f).toInt()
}

fun isDefender(piece: Piece): Boolean {
    return piece.type == PieceType.DEFENDER || piece.type == PieceType.KING
}

// Returns true if position is in a board corner or center
fun inRestricted(pos: Pair<Int, Int>) : Boolean {
    val x = pos.first
    val y = pos.second
    return (x == 0 && y == 0) || (x == 10 && y == 0) || (x == 0 && y == 10) || (x == 10 && y == 10) || (x == 5 && y == 5)
}