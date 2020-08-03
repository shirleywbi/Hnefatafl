package com.shirleywbi.hnefatafl.service

import com.shirleywbi.hnefatafl.service.pieces.PieceType
import java.io.Serializable

class Game: Serializable {

    lateinit var piece: PieceType
    var gameBoard: Board = Board()

    fun selectPlayer(type: PieceType) {
        piece = type
        gameBoard.playerType = type
    }

}