package com.shirleywbi.hnefatafl

import com.shirleywbi.hnefatafl.pieces.ChessPiece
import com.shirleywbi.hnefatafl.pieces.KingPiece
import com.shirleywbi.hnefatafl.pieces.Piece
import com.shirleywbi.hnefatafl.pieces.PlayerType

class Board {

    val boardWidth = 11
    val boardHeight = 11

    var pieces: ArrayList<Piece> = arrayListOf()
    var occupied = Array(boardHeight) { BooleanArray(boardWidth) }
    var player: PlayerType

    constructor(player: PlayerType) {
        this.player = player
        setupBoard()
        setupPieces()
    }

    fun canMove(piece: ChessPiece, x: Int, y: Int): Boolean {
        return piece.canMove(x, y, occupied, player)
    }

    fun move(piece: ChessPiece, x: Int, y: Int) {
        if (canMove(piece, x, y)) {
            occupied[piece.x][piece.y] = false
            piece.move(x, y)
            occupied[x][y] = true
        } else {
            throw Exception("Invalid move")
        }
    }

    private fun setupBoard() {
        for (x in 0..10) {
            occupied[0][x] = x in 3..7
            occupied[1][x] = x == 5
            occupied[2][x] = false
            occupied[3][x] = x == 0 || x == 5 || x == 10
            occupied[4][x] = x == 0 || x in 4..6 || x == 10
            occupied[5][x] = x != 2 && x != 8
            occupied[6][x] = x == 0 || x in 4..6 || x == 10
            occupied[7][x] = x == 0 || x == 5 || x == 10
            occupied[8][x] = false
            occupied[9][x] = x == 5
            occupied[10][x] = x in 3..7
        }
    }

    private fun setupPieces(): Unit {
        // set up attackers
        for (x in 0..10) {
            if (x in 3..7) {
                pieces.add(ChessPiece(x, 0, PlayerType.ATTACKER))
                pieces.add(ChessPiece(x, 10, PlayerType.ATTACKER))
            }
            if (x == 5) {
                pieces.add(ChessPiece(x, 1, PlayerType.ATTACKER))
                pieces.add(ChessPiece(x, 9, PlayerType.ATTACKER))
            }
            if (x == 0 || x == 10) for (y in 3..7) pieces.add(ChessPiece(x, y, PlayerType.ATTACKER))
            if (x == 1 || x == 9) pieces.add(ChessPiece(x, 5, PlayerType.ATTACKER))
        }

        // set up defenders
        for (x in 3..7) {
            if (x == 3 || x == 7) pieces.add(ChessPiece(x, 5, PlayerType.DEFENDER))
            if (x == 4 || x == 6) for (y in 4..6) pieces.add(ChessPiece(x, y, PlayerType.DEFENDER))
            if (x == 5) {
                for (y in 3..4) pieces.add(ChessPiece(x, y, PlayerType.DEFENDER))
                pieces.add(KingPiece(5, 5))
                for (y in 6..7) pieces.add(ChessPiece(x, y, PlayerType.DEFENDER))
            }
        }
    }
}