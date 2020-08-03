package com.shirleywbi.hnefatafl.service.pieces

class KingPiece(x: Int, y: Int, type: PieceType = PieceType.KING, label: String = "king") : Piece(x, y, type, label) {

    // King is on the corner of the board
    fun hasWon(): Boolean {
        return (x == 0 && y == 0) || (x == 10 && y == 0) || (x == 0 && y == 10) || (x == 10 && y == 10)
    }

}