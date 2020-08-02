package com.shirleywbi.hnefatafl.service.pieces

import java.io.Serializable
import kotlin.math.abs

abstract class Piece(var x: Int, var y: Int, var type: PieceType, var label: String): Serializable {

    /**
     * Returns true if:
     * (1) It is the player's piece
     * (2) Nothing is in the way
     */
    open fun canMove(newX: Int, newY: Int, layoutMap: HashMap<Pair<Int, Int>, Piece>, piece: PieceType): Boolean {
        if (type != piece || layoutMap.containsKey(Pair(newX, newY))) return false

        if (newX == x && newY == y || // movement: none
            newX != x && newY != y) { // movement: diagonal
            return false
        } else if (newX == x) { // movement: vertical
            val dir = if (y < newY) 1 else -1
            for (i in 1..abs(y - newY)) {
                if(layoutMap.containsKey(Pair(x, y + i * dir))) {
                    return false
                }
            }
        } else if (newY == y) { // movement: horizontal
            val dir = if (x < newX) 1 else -1
            for (i in 1..abs(x - newX)) {
                if (layoutMap.containsKey(Pair(x + i * dir, y))) {
                    return false
                }
            }
        }
        return true
    }

    fun move(newX: Int, newY: Int) {
        x = newX
        y = newY
    }

    fun capture(x: Int, y: Int, layoutMap: HashMap<Pair<Int, Int>, Piece>, piece: PieceType): List<Piece> {
        var captures: ArrayList<Piece> = arrayListOf()
        if (isCaptured(x, y, 1, 0, layoutMap, piece)) {
            layoutMap[Pair(x + 1, y)]?.let { captures.add(it) }
            layoutMap.remove(Pair(x + 1, y))
        }
        if (isCaptured(x, y, -1, 0, layoutMap, piece)) {
            layoutMap[Pair(x - 1, y)]?.let { captures.add(it) }
            layoutMap.remove(Pair(x - 1, y))
        }
        if (isCaptured(x, y, 0, 1, layoutMap, piece)) {
            layoutMap[Pair(x, y + 1)]?.let { captures.add(it) }
            layoutMap.remove(Pair(x, y + 1))
        }
        if (isCaptured(x, y, 0, -1, layoutMap, piece)) {
            layoutMap[Pair(x, y - 1)]?.let { captures.add(it) }
            layoutMap.remove(Pair(x, y - 1))
        }
        return captures
    }

    fun getCapturedPositions(x: Int, y: Int, layoutMap: HashMap<Pair<Int, Int>, Piece>, piece: PieceType): List<Pair<Int, Int>> {
        var captures: ArrayList<Pair<Int, Int>> = arrayListOf()
        if (isCaptured(x, y, 1, 0, layoutMap, piece)) {
            layoutMap[Pair(x + 1, y)]?.let { captures.add(Pair(x + 1, y)) }
        }
        if (isCaptured(x, y, -1, 0, layoutMap, piece)) {
            layoutMap[Pair(x - 1, y)]?.let { captures.add(Pair(x - 1, y)) }
        }
        if (isCaptured(x, y, 0, 1, layoutMap, piece)) {
            layoutMap[Pair(x, y + 1)]?.let { captures.add(Pair(x, y + 1)) }
        }
        if (isCaptured(x, y, 0, -1, layoutMap, piece)) {
            layoutMap[Pair(x, y - 1)]?.let { captures.add(Pair(x, y - 1)) }
        }
        return captures
    }

    // Capture if piece is surrounded vertically or horizontally by a piece or an empty restricted spot
    private fun isCaptured(x: Int, y: Int, xOffset: Int = 0, yOffset: Int = 0, layoutMap: HashMap<Pair<Int, Int>, Piece>, piece: PieceType): Boolean {
        val adjacentPieceIsDifferent = layoutMap[Pair(x + xOffset, y + yOffset)]?.type != piece
        val hasPieceFlank = layoutMap[Pair(x + xOffset * 2, y + yOffset * 2)]?.type == piece
        val hasCellFlank = !layoutMap.containsKey(Pair(x + xOffset * 2, y + yOffset * 2)) && inRestricted(x + xOffset * 2, y + yOffset * 2)
        return adjacentPieceIsDifferent && (hasPieceFlank || hasCellFlank)
    }

    // Returns true if position is in a board corner or center
    private fun inRestricted(x: Int, y: Int) : Boolean {
        return (x == 0 && y == 0) || (x == 10 && y == 0) || (x == 0 && y == 10) || (x == 10 && y == 10) || (x == 5 && y == 5)
    }

}