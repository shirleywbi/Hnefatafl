package com.shirleywbi.hnefatafl.service.pieces

class ChessPiece(x: Int, y: Int, type: PieceType): Piece(x, y, type) {

    // Non-king pieces cannot move to restricted positions
    override fun canMove(
        newX: Int,
        newY: Int,
        layoutMap: HashMap<Pair<Int, Int>, Piece>,
        piece: PieceType
    ): Boolean {
        if ((x == 0 && y == 0) || (x == 10 && y == 0) || (x == 0 && y == 10) || (x == 10 && y == 10)) return false
        return super.canMove(newX, newY, layoutMap, piece)
    }

}