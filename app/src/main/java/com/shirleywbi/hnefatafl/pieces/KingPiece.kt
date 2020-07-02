package com.shirleywbi.hnefatafl

class KingPiece(x: Int, y: Int, type: PlayerType = PlayerType.DEFENDER) : ChessPiece(x, y, type) {

    override fun canMove(newX: Int, newY: Int): Boolean {
        // cannot move on top of another piece
        // cannot move past other board pieces
        // x and y stay the same
        //
        return true
    }

    fun win() {

    }
}