package com.shirleywbi.hnefatafl.service.pieces

import com.shirleywbi.hnefatafl.util.inRestricted
import java.io.Serializable
import kotlin.math.abs

abstract class Piece(var x: Int, var y: Int, var type: PieceType, var label: String): Serializable {

    /**
     * Returns true if:
     * (1) It is the player's piece
     * (2) Nothing is in the way
     */
    open fun canMove(newX: Int, newY: Int, layoutMap: HashMap<Pair<Int, Int>, Piece>, piece: PieceType): Boolean {
        if (layoutMap.containsKey(Pair(newX, newY))) return false

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

    fun capturePositionsAndPieces(x: Int, y: Int, layoutMap: HashMap<Pair<Int, Int>, Piece>): HashMap<Pair<Int, Int>, Piece> {
        val captures: HashMap<Pair<Int, Int>, Piece> = hashMapOf()
        val surroundingPos = getSurroundingPos(Pair(x, y))
        for (pos in surroundingPos) {
            val hasAdjacentEnemy = layoutMap.containsKey(pos) && layoutMap[pos]?.type != layoutMap[Pair(x, y)]?.type
            if (hasAdjacentEnemy) {
                handleCapture(Pair(x, y), layoutMap[pos]!!, layoutMap, layoutMap[Pair(x, y)]?.type!!, captures)
            }
        }
        return captures
    }

    private fun handleCapture(movedPiecePos: Pair<Int, Int>, possibleCapture: Piece, layoutMap: HashMap<Pair<Int, Int>, Piece>, player: PieceType, captures: HashMap<Pair<Int, Int>, Piece>) {
        if (possibleCapture is ChessPiece) {
            handlePieceCapture(movedPiecePos, possibleCapture, layoutMap, player, captures)
        } else {
            handleKingCapture(movedPiecePos, layoutMap, player, captures)
        }
    }

    // Remove piece and store in captures if piece is flanked vertically or horizontally by a piece of empty restricted spot
    private fun handlePieceCapture(movedPiecePos: Pair<Int, Int>, possibleCapture: Piece, layoutMap: HashMap<Pair<Int, Int>, Piece>, player: PieceType, captures: HashMap<Pair<Int, Int>, Piece>) {
        val xOffset = possibleCapture.x - movedPiecePos.first
        val yOffset = possibleCapture.y - movedPiecePos.second

        val flankPos = Pair(possibleCapture.x + xOffset, possibleCapture.y + yOffset)
        val isFlankedByEnemyPiece = layoutMap.containsKey(flankPos) && layoutMap[flankPos]?.type == player
        val isFlankedByCell = inRestricted(flankPos)
        if (isFlankedByEnemyPiece || isFlankedByCell) {
            captures[Pair(possibleCapture.x, possibleCapture.y)] = possibleCapture
            layoutMap.remove(Pair(possibleCapture.x, possibleCapture.y))
        }
    }

    /**
     * King is captured as an individual piece when:
     * (1) Flanked in all 4 directions
     * (2) Flanked in 3 directions + throne
     * (3) Flanked in 3 directions + edge (if only King remaining)
     */
    private fun handleKingCapture(movedPiecePos: Pair<Int, Int>, layoutMap: HashMap<Pair<Int, Int>, Piece>, player: PieceType, captures: HashMap<Pair<Int, Int>, Piece>) {
        if (player == PieceType.DEFENDER) return
        var kingPos: Pair<Int, Int> = Pair(-1, -1)
        var surroundingPos: List<Pair<Int, Int>> = getSurroundingPos(movedPiecePos)
        for (pos in surroundingPos) {
            if (layoutMap[pos] is KingPiece) {
                kingPos = pos
            }
        }
        if (kingPos == Pair(-1, -1)) return
        surroundingPos = getSurroundingPos(kingPos)

        var flankedDirections = 0
        for (pos in surroundingPos) {
            val isAttacker = layoutMap[pos]?.type == PieceType.ATTACKER
            val isThrone = pos == Pair(5, 5)
            if (isAttacker || isThrone) {
                flankedDirections += 1
            }
        }
        val isEdge = kingPos.first == 0 || kingPos.first == 10 || kingPos.second == 0 || kingPos.second == 10
        val isLastDefender = true // TODO: Add count to game to check if last defender
        if (flankedDirections == 4 || (flankedDirections == 3 && isEdge && isLastDefender)) {
            captures[kingPos] = layoutMap[kingPos]!!
            layoutMap.remove(kingPos)
        }
    }

    private fun getSurroundingPos(pos: Pair<Int, Int>): List<Pair<Int, Int>> {
        val x = pos.first
        val y = pos.second
        return arrayListOf(Pair(x-1, y), Pair(x+1, y), Pair(x, y-1), Pair(x, y+1))
    }

}