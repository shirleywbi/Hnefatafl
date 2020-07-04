package com.shirleywbi.hnefatafl.service

import com.shirleywbi.hnefatafl.service.pieces.ChessPiece
import com.shirleywbi.hnefatafl.service.pieces.KingPiece
import com.shirleywbi.hnefatafl.service.pieces.Piece
import com.shirleywbi.hnefatafl.service.pieces.PlayerType

class Board {

    var isGameOver = false
    var isAttackerTurn = true

    var layoutMap: HashMap<Pair<Int, Int>, Piece> = hashMapOf()
    var player: PlayerType

    constructor(player: PlayerType) {
        this.player = player
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
        return piece.canMove(x, y, layoutMap, player)
    }

    private fun checkDefenderWin(piece: Piece) {
        if (piece is KingPiece && piece.hasWon()) {
            isGameOver = true
            throw Exception("Game over. ${if (player == PlayerType.DEFENDER) "You have won!" else "You have lost. Please try again!"}.")
        }
    }

    private fun checkAttackerWin(piece: Piece, x: Int, y: Int) {
        if (piece.type == PlayerType.ATTACKER) {
            var captures = piece.capture(x, y, layoutMap, player)
            captures.forEach { capture -> if (capture is KingPiece) isGameOver = true}
            if (isGameOver) throw Exception("Game over. ${if (player == PlayerType.ATTACKER) "You have won!" else "You have lost. Please try again!"}.")
        }
    }

    private fun setupPieces() {
        // set up attackers
        for (x in 0..10) {
            if (x in 3..7) {
                layoutMap[Pair(x, 0)] = ChessPiece(x, 0, PlayerType.ATTACKER)
                layoutMap[Pair(x, 10)] = ChessPiece(x, 10, PlayerType.ATTACKER)
            }
            if (x == 5) {
                layoutMap[Pair(x, 1)] = ChessPiece(x, 1, PlayerType.ATTACKER)
                layoutMap[Pair(x, 9)] = ChessPiece(x, 9, PlayerType.ATTACKER)
            }
            if (x == 0 || x == 10) for (y in 3..7) layoutMap[Pair(x, y)] = ChessPiece(x, y, PlayerType.ATTACKER)
            if (x == 1 || x == 9) layoutMap[Pair(x, 5)] = ChessPiece(x, 5, PlayerType.ATTACKER)
        }

        // set up defenders
        for (x in 3..7) {
            if (x == 3 || x == 7) layoutMap[Pair(x, 5)] = ChessPiece(x, 5, PlayerType.DEFENDER)
            if (x == 4 || x == 6) for (y in 4..6) layoutMap[Pair(x, y)] = ChessPiece(x, y, PlayerType.DEFENDER)
            if (x == 5) {
                for (y in 3..4) layoutMap[Pair(x, y)] = ChessPiece(x, y, PlayerType.DEFENDER)
                layoutMap[Pair(5, 5)] = KingPiece(5, 5)
                for (y in 6..7) layoutMap[Pair(x, y)] = ChessPiece(x, y, PlayerType.DEFENDER)
            }
        }
    }
}