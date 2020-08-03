package com.shirleywbi.hnefatafl.service.pieces

import com.shirleywbi.hnefatafl.util.inRestricted

class ChessPiece(x: Int, y: Int, type: PieceType, label: String): Piece(x, y, type, label) {

    // Non-king pieces cannot move to restricted positions
    override fun canMove(
        newX: Int,
        newY: Int,
        layoutMap: HashMap<Pair<Int, Int>, Piece>,
        piece: PieceType
    ): Boolean {
        if (inRestricted(Pair(newX, newY))) return false
        return super.canMove(newX, newY, layoutMap, piece)
    }

}