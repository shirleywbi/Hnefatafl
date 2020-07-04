package com.shirleywbi.hnefatafl.service.pieces

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

    fun capture(x: Int, y: Int, layoutMap: HashMap<Pair<Int, Int>, Piece>, player: PlayerType): List<Piece> {
        var captures: ArrayList<Piece> = arrayListOf()
        if (isCapture(x, y, 1, 0, layoutMap, player)) {
            layoutMap[Pair(x + 1, y)]?.let { captures.add(it) }
            layoutMap.remove(Pair(x + 1, y))
        }
        if (isCapture(x, y, -1, 0, layoutMap, player)) {
            layoutMap[Pair(x - 1, y)]?.let { captures.add(it) }
            layoutMap.remove(Pair(x - 1, y))
        }
        if (isCapture(x, y, 0, 1, layoutMap, player)) {
            layoutMap[Pair(x, y + 1)]?.let { captures.add(it) }
            layoutMap.remove(Pair(x, y + 1))
        }
        if (isCapture(x, y, 0, -1, layoutMap, player)) {
            layoutMap[Pair(x, y - 1)]?.let { captures.add(it) }
            layoutMap.remove(Pair(x, y - 1))
        }
        return captures
    }

    // Capture if piece is surrounded vertically or horizontally by a piece or an empty restricted spot
    private fun isCapture(x: Int, y: Int, xOffset: Int = 0, yOffset: Int = 0, layoutMap: HashMap<Pair<Int, Int>, Piece>, player: PlayerType): Boolean {
        return (layoutMap[Pair(x + xOffset, y + yOffset)]?.type != player &&
            (layoutMap[Pair(x + xOffset * 2, y + yOffset * 2)]?.type == player ||
            (!layoutMap.containsKey(Pair(x + xOffset * 2, y + yOffset * 2)) && inRestricted(x + xOffset * 2, y + yOffset * 2))))
    }

    // Returns true if position is in a board corner or center
    private fun inRestricted(x: Int, y: Int) : Boolean {
        return (x == 0 && y == 0) || (x == 10 && y == 0) || (x == 0 && y == 10) || (x == 10 && y == 10) || (x == 5 && y == 5)
    }

}