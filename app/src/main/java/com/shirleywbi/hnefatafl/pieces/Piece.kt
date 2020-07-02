package com.shirleywbi.hnefatafl.pieces

abstract class Piece(var x: Int, var y: Int, var type: PlayerType) {

    /**
     * Returns true if:
     * (1) It is the player's piece
     * (2) Nothing is in the way
     */
    open fun canMove(newX: Int, newY: Int, layoutMap: HashMap<Pair<Int, Int>, Piece>, player: PlayerType): Boolean {
        if (type != player) return false

        if (newX == x && newY == y || // movement: none
            newX != x && newY != y) { // movement: diagonal
            return false
        } else if (newX == x) { // movement: vertical
            for (i in 1..(x - newX)) {
                if(layoutMap.containsKey(Pair(newX, newY + i))) return false
            }
        } else if (newY == y) { // movement: horizontal
            for (i in 1..(x - newX)) {
                if (layoutMap.containsKey(Pair(newX + i, newY))) return false
            }
        }
        return true
    }

    fun move(newX: Int, newY: Int) {
        x = newX
        y = newY
    }

    // TODO: Determine how pieces are captured
    fun getCaptured() : Unit {
        x = -1
        y = -1
    }

}