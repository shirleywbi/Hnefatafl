package com.shirleywbi.hnefatafl.service.pieces

class ChessPiece(x: Int, y: Int, type: PieceType, label: String): Piece(x, y, type, label) {

    // Non-king pieces cannot move to restricted positions
    override fun canMove(
        newX: Int,
        newY: Int,
        layoutMap: HashMap<Pair<Int, Int>, Piece>,
        piece: PieceType
    ): Boolean {
        if ((newX == 0 && newY == 0) || (newX == 10 && newY == 0) || (newX == 0 && newY == 10) || (newX == 10 && newY == 10)) return false
        return super.canMove(newX, newY, layoutMap, piece)
    }

}