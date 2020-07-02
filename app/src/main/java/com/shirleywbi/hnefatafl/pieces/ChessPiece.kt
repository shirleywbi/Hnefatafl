package com.shirleywbi.hnefatafl

open class ChessPiece(var x: Int, var y: Int, var type: PlayerType) {

    open fun canMove(newX: Int, newY: Int) : Boolean {
        if (isMyPiece && notBlocked)
        return true
    }

    fun move(newX: Int, newY: Int) : Unit {
        if (canMove(newX, newY)) {
            x = newX
            y = newY
        }
    }

    fun getCaptured() : Unit {
        x = -1
        y = -1
    }
}