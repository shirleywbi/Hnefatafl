package com.shirleywbi.hnefatafl.service

import com.shirleywbi.hnefatafl.service.pieces.ChessPiece
import com.shirleywbi.hnefatafl.service.pieces.KingPiece
import com.shirleywbi.hnefatafl.service.pieces.Piece
import com.shirleywbi.hnefatafl.service.pieces.PieceType
import com.shirleywbi.hnefatafl.util.getSurroundingPos
import com.shirleywbi.hnefatafl.util.isDefender
import java.io.Serializable

class Board: Serializable {

    var isGameOver = false
    lateinit var gameOverStatus: GameOverStatus
    var isAttackerTurn = true

    var layoutMap: HashMap<Pair<Int, Int>, Piece>
    var boardHistory: HashMap<HashMap<Pair<Int, Int>, Piece>, Int> = hashMapOf()
    lateinit var playerType: PieceType

    constructor(layoutMap: HashMap<Pair<Int, Int>, Piece>) {
        this.layoutMap = layoutMap
    }

    // Alternate turns to determine whether piece can move, starting with attacker
    fun canMove(piece: Piece?, x: Int, y: Int): Boolean {
        if (piece != null) {
            if (piece.type == PieceType.ATTACKER && isAttackerTurn)
                return piece.canMove(x, y, layoutMap, this.playerType)
            if (isDefender(piece) && !isAttackerTurn)
                return piece.canMove(x, y, layoutMap, this.playerType)
        }
        return false
    }

    fun move(piece: Piece, x: Int, y: Int) {
        if (canMove(piece, x, y)) {
            layoutMap.remove(Pair(piece.x, piece.y))
            piece.move(x, y)
            layoutMap[Pair(x, y)] = piece
            logBoardHistory()

            checkDefenderWin(piece)
//            checkAttackerWin(piece) // TODO
            nextTurn()
            checkDraw()
        } else {
            throw Exception("Invalid move")
        }
    }

    private fun nextTurn() {
        isAttackerTurn = !isAttackerTurn
    }

    private fun logBoardHistory() {
        boardHistory[layoutMap] = if (boardHistory.containsKey(layoutMap)) boardHistory[layoutMap]!!.plus(1) else 1
    }

    private fun checkDefenderWin(piece: Piece) {
        val lessThanThreeAttackers = layoutMap.values.filter{ p -> p.type == PieceType.ATTACKER }.size < 3
        if (piece is KingPiece && piece.hasWon() || lessThanThreeAttackers) {
            isGameOver = true
            gameOverStatus = if (this.playerType == PieceType.DEFENDER) GameOverStatus.WIN else GameOverStatus.LOSE
        }
    }

    fun checkAttackerWin(captured: Piece) {
        if (captured is KingPiece) {
            isGameOver = true
            gameOverStatus = if (this.playerType == PieceType.ATTACKER) GameOverStatus.WIN else GameOverStatus.LOSE
        }
    }

    /**
     * Return true if:
     * (1) Either player has no more moves on their turn
     * (2) The king is at the side of the board and the attacking player cannot move him into a position where he can be taken **NOT IMPLEMENTED**
     * (3) The king cannot reach a refuge square and the attacker cannot reach the king **NOT IMPLEMENTED**
     * (4) The same position of all pieces on the board arises 3x with the same side to move
     */
    private fun checkDraw() {
        val tooManyRepetitions = boardHistory[layoutMap]!! >= 3
        val noMoreMoves = if (isAttackerTurn) checkNoMoreMoves(PieceType.ATTACKER) else checkNoMoreMoves(PieceType.DEFENDER) || checkNoMoreMoves(PieceType.KING)
        if (tooManyRepetitions || noMoreMoves) {
            isGameOver = true
            gameOverStatus = GameOverStatus.DRAW
        }
    }

    private fun checkNoMoreMoves(turn: PieceType): Boolean {
        val pieces = layoutMap.values.filter{ piece -> piece.type == turn }
        for (piece in pieces) {
            var surroundings = getSurroundingPos(Pair(piece.x, piece.y))
            for (surrounding in surroundings) {
                if (!layoutMap.containsKey(surrounding)) {
                    return false
                }
            }
        }
        return true
    }

}