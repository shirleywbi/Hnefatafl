package com.shirleywbi.hnefatafl

import com.shirleywbi.hnefatafl.pieces.PlayerType

class Game {

    lateinit var player: PlayerType
    lateinit var gameBoard: Board
    var isAttackerTurn: Boolean = true

    fun selectPlayer(type: PlayerType) {
        player = type
        gameBoard = Board(player)
    }

    fun nextTurn() : Unit {
        isAttackerTurn = !isAttackerTurn
    }

}