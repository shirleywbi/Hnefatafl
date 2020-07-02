package com.shirleywbi.hnefatafl

import com.shirleywbi.hnefatafl.pieces.PlayerType

class Game {

    lateinit var player: PlayerType
    lateinit var gameBoard: Board

    fun selectPlayer(type: PlayerType) {
        player = type
        gameBoard = Board(player)
    }

}