package com.shirleywbi.hnefatafl.pieces

class KingPiece(x: Int, y: Int, type: PlayerType = PlayerType.DEFENDER) : Piece(x, y, type) {

    // King is on the corner of the board
    fun hasWon(): Boolean {
        return (x == 0 && y == 0) || (x == 10 && y == 0) || (x == 0 && y == 10) || (x == 10 && y == 10)
    }

}