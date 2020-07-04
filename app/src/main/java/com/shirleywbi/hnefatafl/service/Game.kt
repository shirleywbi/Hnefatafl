package com.shirleywbi.hnefatafl.service

import com.shirleywbi.hnefatafl.service.Board
import com.shirleywbi.hnefatafl.service.pieces.PlayerType

class Game {

    lateinit var player: PlayerType
    lateinit var gameBoard: Board

    fun selectPlayer(type: PlayerType) {
        player = type
        gameBoard = Board(player)
    }

}