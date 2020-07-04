package com.shirleywbi.hnefatafl.service

import com.shirleywbi.hnefatafl.service.pieces.PieceType

class Game {

    lateinit var piece: PieceType
    var gameBoard: Board = Board()

    fun selectPlayer(type: PieceType) {
        piece = type
        gameBoard.piece = type
    }

}