package com.shirleywbi.hnefatafl

import com.shirleywbi.hnefatafl.service.Board
import com.shirleywbi.hnefatafl.service.pieces.ChessPiece
import com.shirleywbi.hnefatafl.service.pieces.KingPiece
import com.shirleywbi.hnefatafl.service.pieces.Piece
import com.shirleywbi.hnefatafl.service.pieces.PieceType
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

import org.junit.Before

class BoardTest {

    private lateinit var atkBoard: Board
    private lateinit var defBoard: Board
    private lateinit var atk0x5: Piece
    private lateinit var def1x2: Piece
    private lateinit var def3x2: Piece
    private lateinit var atk5x0: Piece
    private lateinit var atk10x3: Piece
    private lateinit var king: Piece

    @Before
    fun init() {
        atk0x5 = ChessPiece(0, 5, PieceType.ATTACKER)
        atk5x0 = ChessPiece(5, 0, PieceType.ATTACKER)
        atk10x3 = ChessPiece(10, 3, PieceType.ATTACKER)
        def1x2 = ChessPiece(1, 2, PieceType.DEFENDER)
        def3x2 = ChessPiece(3, 2, PieceType.DEFENDER)

        atkBoard = Board(hashMapOf(), PieceType.ATTACKER)
        atkBoard.isAttackerTurn = true
        atkBoard.layoutMap = hashMapOf()

        defBoard = Board(hashMapOf(), PieceType.DEFENDER)
        defBoard.isAttackerTurn = false
        defBoard.layoutMap = hashMapOf(
            Pair(1, 2) to def1x2,
            Pair(3, 2) to def3x2
        )
    }

    @Test
    fun canMove() {
        assertTrue(defBoard.canMove(def1x2, 2, 2))
        assertTrue(defBoard.canMove(def1x2, 1, 10))
    }

    @Test
    fun canMove_existingPieceAtPositionShouldFail() {
        assertFalse(defBoard.canMove(def1x2, 3, 2))
    }

    @Test
    fun canMove_diagonalMoveShouldFail() {
        assertFalse(defBoard.canMove(def1x2, 3, 3))
    }

    @Test
    fun canMove_blockedShouldFail() {
        assertFalse(defBoard.canMove(def1x2, 5, 2))
    }

    @Test
    fun canMove_normalToRestrictedShouldFail() {
        assertFalse(atkBoard.canMove(atk0x5, 0, 0))
        assertFalse(atkBoard.canMove(atk0x5, 0, 10))
        assertFalse(atkBoard.canMove(atk0x5, 5, 5))
        assertFalse(atkBoard.canMove(atk5x0, 10, 0))
        assertFalse(atkBoard.canMove(atk10x3, 10, 10))
    }

    @Test
    fun canMove_kingToRestrictedShouldSucceed() {
        king = KingPiece(0, 2, PieceType.KING)
        assertTrue(defBoard.canMove(king, 0, 0))
        assertTrue(defBoard.canMove(king, 0, 10))
        king = KingPiece(10, 2, PieceType.KING)
        assertTrue(defBoard.canMove(king, 10, 0))
        assertTrue(defBoard.canMove(king, 10, 10))
        king = KingPiece(5, 4, PieceType.KING)
        assertTrue(defBoard.canMove(king, 5, 5))
    }
}