package com.shirleywbi.hnefatafl.service

import com.shirleywbi.hnefatafl.service.pieces.ChessPiece
import com.shirleywbi.hnefatafl.service.pieces.KingPiece
import com.shirleywbi.hnefatafl.service.pieces.Piece
import com.shirleywbi.hnefatafl.service.pieces.PieceType
import java.io.Serializable

class Game: Serializable {

    lateinit var piece: PieceType
    lateinit var gameBoard: Board

    fun selectPlayer(type: PieceType) {
        piece = type
        gameBoard = Board(generatePieceLayout(), type)
    }

    private fun generatePieceLayout(): HashMap<Pair<Int, Int>, Piece> {
        var layoutMap: HashMap<Pair<Int, Int>, Piece> = hashMapOf()
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
        return layoutMap
    }
}