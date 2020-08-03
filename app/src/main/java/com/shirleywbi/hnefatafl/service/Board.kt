package com.shirleywbi.hnefatafl.service

import com.shirleywbi.hnefatafl.service.pieces.ChessPiece
import com.shirleywbi.hnefatafl.service.pieces.KingPiece
import com.shirleywbi.hnefatafl.service.pieces.Piece
import com.shirleywbi.hnefatafl.service.pieces.PieceType
import com.shirleywbi.hnefatafl.util.getSurroundingPos
import com.shirleywbi.hnefatafl.util.isDefender
import java.io.Serializable

class Board: Serializable {

    var isGameOver = false
    var isAttackerTurn = true

    var layoutMap: HashMap<Pair<Int, Int>, Piece> = hashMapOf()
    lateinit var piece: PieceType

    constructor() {
        setupPieces()
    }

    // Alternate turns to determine whether piece can move, starting with attacker
    fun canMove(piece: Piece?, x: Int, y: Int): Boolean {
        if (piece != null) {
            if (piece.type == PieceType.ATTACKER && isAttackerTurn)
                return piece.canMove(x, y, layoutMap, this.piece)
            if (isDefender(piece) && !isAttackerTurn)
                return piece.canMove(x, y, layoutMap, this.piece)
        }
        return false
    }

    fun move(piece: Piece, x: Int, y: Int) {
        if (canMove(piece, x, y)) {
            layoutMap.remove(Pair(piece.x, piece.y))
            piece.move(x, y)
            layoutMap[Pair(x, y)] = piece
            checkDefenderWin(piece)
//            checkAttackerWin(piece) // TODO
            checkDraw()
            nextTurn()
        } else {
            throw Exception("Invalid move")
        }
    }

    private fun nextTurn() {
        isAttackerTurn = !isAttackerTurn
    }

    private fun checkDefenderWin(piece: Piece) {
        if (piece is KingPiece && piece.hasWon()) {
            isGameOver = true
            throw Exception("Game over. ${if (this.piece == PieceType.DEFENDER) "You have won!" else "You have lost. Please try again!"}.")
        }
    }

    fun checkAttackerWin(captured: Piece, playerType: PieceType) {
        if (captured is KingPiece) {
            isGameOver = true
            if (isGameOver) throw Exception("Game over. ${if (playerType == PieceType.ATTACKER) "You have won!" else "You have lost. Please try again!"}.")
        }
    }

    fun checkDraw(): Boolean {
        var isDraw = false
        isDraw = if (isAttackerTurn) checkNoMoreMoves(PieceType.ATTACKER) else checkNoMoreMoves(PieceType.DEFENDER) || checkNoMoreMoves(PieceType.KING)
        Log.i("[GAME]", "No more moves. It is a draw!")
        return isDraw
    }

    private fun checkNoMoreMoves(turn: PieceType): Boolean {
        val pieces = layoutMap.values.filter{ piece -> piece.type == turn }
        for (piece in pieces) {
            val surroundings = getSurroundingPos(Pair(piece.x, piece.y))
            for (surrounding in surroundings) {
                if (!layoutMap.containsKey(surrounding)) {
                    return true
                }
            }
        }
        return false
    }

    private fun setupPieces() {
        var attackerCount = 0
        var defenderCount = 0
        // set up attackers
        for (x in 0..10) {
            if (x in 3..7) {
                layoutMap[Pair(x, 0)] = ChessPiece(x, 0, PieceType.ATTACKER, "attacker-${attackerCount++}")
                layoutMap[Pair(x, 10)] = ChessPiece(x, 10, PieceType.ATTACKER, "attacker-${attackerCount++}")
            }
            if (x == 5) {
                layoutMap[Pair(x, 1)] = ChessPiece(x, 1, PieceType.ATTACKER, "attacker-${attackerCount++}")
                layoutMap[Pair(x, 9)] = ChessPiece(x, 9, PieceType.ATTACKER, "attacker-${attackerCount++}")
            }
            if (x == 0 || x == 10) for (y in 3..7) layoutMap[Pair(x, y)] = ChessPiece(x, y, PieceType.ATTACKER, "attacker-${attackerCount++}")
            if (x == 1 || x == 9) layoutMap[Pair(x, 5)] = ChessPiece(x, 5, PieceType.ATTACKER, "attacker-${attackerCount++}")
        }

        // set up defenders
        for (x in 3..7) {
            if (x == 3 || x == 7) layoutMap[Pair(x, 5)] = ChessPiece(x, 5, PieceType.DEFENDER, "defender-${defenderCount++}")
            if (x == 4 || x == 6) for (y in 4..6) layoutMap[Pair(x, y)] = ChessPiece(x, y, PieceType.DEFENDER, "defender-${defenderCount++}")
            if (x == 5) {
                for (y in 3..4) layoutMap[Pair(x, y)] = ChessPiece(x, y, PieceType.DEFENDER, "defender-${defenderCount++}")
                layoutMap[Pair(5, 5)] = KingPiece(5, 5)
                for (y in 6..7) layoutMap[Pair(x, y)] = ChessPiece(x, y, PieceType.DEFENDER, "defender-${defenderCount++}")
            }
        }
    }
}