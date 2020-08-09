package com.shirleywbi.hnefatafl

import com.shirleywbi.hnefatafl.service.Board
import com.shirleywbi.hnefatafl.service.GameOverStatus
import com.shirleywbi.hnefatafl.service.pieces.ChessPiece
import com.shirleywbi.hnefatafl.service.pieces.KingPiece
import com.shirleywbi.hnefatafl.service.pieces.PieceType
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test

class BoardTest {

    lateinit var board: Board

    @Before
    fun init() {
        board = Board(hashMapOf(), PieceType.DEFENDER)
    }

    @Test
    fun checkDefenderWin_defenderInCornerShouldWin() {
        val king = KingPiece(0, 0, PieceType.KING)
        board.layoutMap = hashMapOf(
            Pair(0, 0) to king
        )
        assertFalse(board.isGameOver)
        board.checkDefenderWin(king)
        assertTrue(board.isGameOver)
        assertEquals(GameOverStatus.WIN, board.gameOverStatus)
    }

    @Test
    fun checkDefenderWin_defenderNotInCornerShouldNotWin() {
        val king = KingPiece(5, 5, PieceType.KING)
        board.layoutMap = hashMapOf(
            Pair(5, 5) to king,
            Pair(0, 1) to ChessPiece(0, 1, PieceType.ATTACKER),
            Pair(1, 1) to ChessPiece(1, 1, PieceType.ATTACKER),
            Pair(2, 1) to ChessPiece(2, 1, PieceType.ATTACKER)
        )
        assertFalse(board.isGameOver)
        board.checkDefenderWin(king)
        assertFalse(board.isGameOver)
    }

    @Test
    fun checkDefenderWin_lessThanAttackerLimitShouldWin() {
        val king = KingPiece(5, 5, PieceType.KING)
        board.layoutMap = hashMapOf(
            Pair(5, 5) to king
        )
        assertFalse(board.isGameOver)
        board.checkDefenderWin(king)
        assertTrue(board.isGameOver)
        assertEquals(GameOverStatus.WIN, board.gameOverStatus)
    }

    @Test
    // TODO: Clean up code to fix up this test
    fun checkAttackerWin_capturedKingShouldLose() {
        val moved = ChessPiece(4, 9, PieceType.ATTACKER)
        board.layoutMap = hashMapOf(
            Pair(3, 4) to ChessPiece(3, 4, PieceType.ATTACKER),
            Pair(4, 4) to KingPiece(4, 4, PieceType.KING),
            Pair(5, 4) to ChessPiece(5, 4, PieceType.ATTACKER),
            Pair(4, 3) to ChessPiece(4, 3, PieceType.ATTACKER),
            Pair(4, 9) to moved
        )
        assertFalse(board.isGameOver)
        board.move(moved, 4, 5)
        val captures = moved.capturePositionsAndPieces(4, 3, board.layoutMap)
        captures.values.forEach{ capture ->
            board.checkAttackerWin(capture)
        }
        assertTrue(board.isGameOver)
        assertEquals(GameOverStatus.LOSE, board.gameOverStatus)
    }

    @Test
    fun checkAttackerWin_() {
        // TODO: To be implemented
    }

    @Test
    fun checkDraw_() {
        // TODO: To be implemented
    }

}