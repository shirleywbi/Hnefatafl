package com.shirleywbi.hnefatafl.service

import com.shirleywbi.hnefatafl.service.pieces.ChessPiece
import com.shirleywbi.hnefatafl.service.pieces.KingPiece
import com.shirleywbi.hnefatafl.service.pieces.Piece
import com.shirleywbi.hnefatafl.service.pieces.PieceType

class Board {

    var isGameOver = false
    var isAttackerTurn = true

    var layoutMap: HashMap<Pair<Int, Int>, Piece> = hashMapOf()
    lateinit var piece: PieceType

    constructor() {
        setupPieces()
    }

    // TODO: Handle exceptions
    fun move(piece: Piece, x: Int, y: Int) {
        if (canMove(piece, x, y)) {
            layoutMap.remove(Pair(piece.x, piece.y))
            piece.move(x, y)
            layoutMap[Pair(x, y)] = piece
            checkDefenderWin(piece)
            checkAttackerWin(piece, x, y)
            nextTurn()
        } else {
            throw Exception("Invalid move")
        }
    }

    private fun nextTurn() {
        isAttackerTurn = !isAttackerTurn
    }

    private fun canMove(piece: Piece, x: Int, y: Int): Boolean {
        return piece.canMove(x, y, layoutMap, this.piece)
    }

    private fun checkDefenderWin(piece: Piece) {
        if (piece is KingPiece && piece.hasWon()) {
            isGameOver = true
            throw Exception("Game over. ${if (this.piece == PieceType.DEFENDER) "You have won!" else "You have lost. Please try again!"}.")
        }
    }

    private fun checkAttackerWin(piece: Piece, x: Int, y: Int) {
        if (piece.type == PieceType.ATTACKER) {
            var captures = piece.capture(x, y, layoutMap, this.piece)
            captures.forEach { capture -> if (capture is KingPiece) isGameOver = true}
            if (isGameOver) throw Exception("Game over. ${if (this.piece == PieceType.ATTACKER) "You have won!" else "You have lost. Please try again!"}.")
        }
    }

    private fun setupPieces() {
        // set up attackers
        for (x in 0..10) {
            if (x in 3..7) {
                layoutMap[Pair(x, 0)] = ChessPiece(x, 0, PieceType.ATTACKER)
                layoutMap[Pair(x, 10)] = ChessPiece(x, 10, PieceType.ATTACKER)
            }
            if (x == 5) {
                layoutMap[Pair(x, 1)] = ChessPiece(x, 1, PieceType.ATTACKER)
                layoutMap[Pair(x, 9)] = ChessPiece(x, 9, PieceType.ATTACKER)
            }
            if (x == 0 || x == 10) for (y in 3..7) layoutMap[Pair(x, y)] = ChessPiece(x, y, PieceType.ATTACKER)
            if (x == 1 || x == 9) layoutMap[Pair(x, 5)] = ChessPiece(x, 5, PieceType.ATTACKER)
        }

        // set up defenders
        for (x in 3..7) {
            if (x == 3 || x == 7) layoutMap[Pair(x, 5)] = ChessPiece(x, 5, PieceType.DEFENDER)
            if (x == 4 || x == 6) for (y in 4..6) layoutMap[Pair(x, y)] = ChessPiece(x, y, PieceType.DEFENDER)
            if (x == 5) {
                for (y in 3..4) layoutMap[Pair(x, y)] = ChessPiece(x, y, PieceType.DEFENDER)
                layoutMap[Pair(5, 5)] = KingPiece(5, 5)
                for (y in 6..7) layoutMap[Pair(x, y)] = ChessPiece(x, y, PieceType.DEFENDER)
            }
        }
    }
}