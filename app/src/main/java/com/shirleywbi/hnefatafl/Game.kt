package com.shirleywbi.hnefatafl

import com.shirleywbi.hnefatafl.pieces.PlayerType

class Game {

    var gameBoard: Board = Board()
    lateinit var player: PlayerType
    var isAttackerTurn: Boolean = true

    fun selectPlayer(type: PlayerType) : Unit {
        player = type
    }

    fun nextTurn() : Unit {
        isAttackerTurn = !isAttackerTurn
    }

}