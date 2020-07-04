package com.shirleywbi.hnefatafl.service

import com.shirleywbi.hnefatafl.service.pieces.PlayerType

class Game {

    lateinit var player: PlayerType
    var gameBoard: Board = Board()

    fun selectPlayer(type: PlayerType) {
        player = type
        gameBoard.player = type
    }

}