package com.shirleywbi.hnefatafl

import com.shirleywbi.hnefatafl.service.Board
import com.shirleywbi.hnefatafl.service.pieces.ChessPiece
import com.shirleywbi.hnefatafl.service.pieces.KingPiece
import com.shirleywbi.hnefatafl.service.pieces.Piece
import com.shirleywbi.hnefatafl.service.pieces.PieceType
import com.shirleywbi.hnefatafl.service.pieces.PieceType.*
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test

class PieceTest {
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
        atk0x5 = ChessPiece(0, 5, ATTACKER)
        atk5x0 = ChessPiece(5, 0, ATTACKER)
        atk10x3 = ChessPiece(10, 3, ATTACKER)
        def1x2 = ChessPiece(1, 2, DEFENDER)
        def3x2 = ChessPiece(3, 2, DEFENDER)

        atkBoard = Board(hashMapOf(), ATTACKER)
        atkBoard.isAttackerTurn = true
        atkBoard.layoutMap = hashMapOf()

        defBoard = Board(hashMapOf(), DEFENDER)
        defBoard.isAttackerTurn = false
        defBoard.layoutMap = hashMapOf(
            Pair(1, 2) to def1x2,
            Pair(3, 2) to def3x2
        )
    }

    // Movement tests

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
        king = KingPiece(0, 2, KING)
        assertTrue(defBoard.canMove(king, 0, 0))
        assertTrue(defBoard.canMove(king, 0, 10))
        king = KingPiece(10, 2, KING)
        assertTrue(defBoard.canMove(king, 10, 0))
        assertTrue(defBoard.canMove(king, 10, 10))
        king = KingPiece(5, 4, KING)
        assertTrue(defBoard.canMove(king, 5, 5))
    }

    // Capture Tests

    @Test
    fun capture_attackerFlanksDefenderShouldSucceed() {
        assertCapture(ATTACKER, DEFENDER, ATTACKER, atkBoard, true)
    }

    @Test
    fun capture_defenderFlanksAttackerShouldSucceed() {
        assertCapture(DEFENDER, ATTACKER, DEFENDER, defBoard, true)
    }

    @Test
    fun capture_attackersOnlyShouldFail() {
        assertCapture(ATTACKER, ATTACKER, ATTACKER, atkBoard, false)
    }

    @Test
    fun capture_defendersOnlyShouldFail() {
        assertCapture(DEFENDER, DEFENDER, DEFENDER, defBoard, false)
    }

    @Test
    fun capture_defenderDefenderAttackerShouldFail() {
        assertCapture(ATTACKER, DEFENDER, DEFENDER, defBoard, false)
    }

    @Test
    fun capture_attackerAttackerDefenderShouldFail() {
        assertCapture(DEFENDER, DEFENDER, ATTACKER, atkBoard, false)
    }

    @Test
    fun capture_attackersFlankKingShouldFail() {
        assertCapture(ATTACKER, KING, ATTACKER, atkBoard, false)
    }

    @Test
    fun capture_kingDefenderFlankAttackerShouldSucceed() {
        assertCapture(DEFENDER, ATTACKER, KING, defBoard, true)
    }

    @Test
    fun capture_kingDefenderFlankDefenderShouldFail() {
        assertCapture(DEFENDER, DEFENDER, KING, defBoard, false)
    }

    @Test
    fun capture_kingAttackerFlankDefenderShouldFail() {
        assertCapture(ATTACKER, DEFENDER, KING, defBoard, false)
    }

    @Test
    fun capture_kingAttackerFlankAttackerShouldFail() {
        assertCapture(ATTACKER, ATTACKER, KING, defBoard, false)
    }

    private fun assertCapture(flanker: PieceType, captured: PieceType, moved: PieceType, board: Board, succeed: Boolean) {
        val piece1 = if (flanker == KING) KingPiece(0, 3, flanker) else ChessPiece(0, 3, flanker)
        val piece2 = if (captured == KING) KingPiece(1, 3, captured) else ChessPiece(1, 3, captured)
        val piece3 = if (moved == KING) KingPiece(3, 3, moved) else ChessPiece(3, 3, moved)

        board.layoutMap = hashMapOf(
            Pair(0, 3) to piece1,
            Pair(1, 3) to piece2,
            Pair(3, 3) to piece3
        )

        assertTrue(board.layoutMap.containsValue(piece2))
        board.move(piece3, 2, 3)
        val captures = piece3.capturePositionsAndPieces(2, 3, board.layoutMap)
        if (succeed) {
            assertTrue(captures.size == 1)
            assertTrue(captures.containsValue(piece2))
            assertFalse(board.layoutMap.containsValue(piece2))
        } else {
            assertTrue(captures.size == 0)
            assertTrue(board.layoutMap.containsValue(piece2))
        }
    }

    @Test
    fun capture_multiplePieces() {
        val piece1 = ChessPiece(0, 3, ATTACKER)
        val piece2 = ChessPiece(1, 3, DEFENDER)
        val piece3 = ChessPiece(3, 3, ATTACKER)
        val piece4 = ChessPiece(2, 4, DEFENDER)
        val piece5 = ChessPiece(2, 5, ATTACKER)

        atkBoard.layoutMap = hashMapOf(
            Pair(0, 3) to piece1,
            Pair(1, 3) to piece2,
            Pair(3, 3) to piece3,
            Pair(2, 4) to piece4,
            Pair(2, 5) to piece5
        )

        assertTrue(atkBoard.layoutMap.containsValue(piece2))
        assertTrue(atkBoard.layoutMap.containsValue(piece4))
        atkBoard.move(piece3, 2, 3)
        val captures = piece3.capturePositionsAndPieces(2, 3, atkBoard.layoutMap)
        assertTrue(captures.size == 2)
        assertTrue(captures.containsValue(piece2))
        assertTrue(captures.containsValue(piece4))
        assertFalse(atkBoard.layoutMap.containsValue(piece2))
        assertFalse(atkBoard.layoutMap.containsValue(piece4))
    }

    @Test
    fun capture_flankedByRestrictedCellShouldSucceed() {
        val piece1 = ChessPiece(5, 6, ATTACKER)
        val piece2 = ChessPiece(5, 9, DEFENDER)

        defBoard.layoutMap = hashMapOf(
            Pair(5, 6) to piece1,
            Pair(5, 9) to piece2
        )

        assertTrue(defBoard.layoutMap.containsValue(piece1))
        defBoard.move(piece2, 5, 7)
        val captures = piece2.capturePositionsAndPieces(5, 7, defBoard.layoutMap)
        assertTrue(captures.size == 1)
        assertTrue(captures.containsValue(piece1))
        assertFalse(defBoard.layoutMap.containsValue(piece1))
    }

    @Test
    fun capture_flankedByNonEmptyRestrictedCellShouldFail() {
        val king = KingPiece(5, 5, KING)
        val piece1 = ChessPiece(5, 6, DEFENDER)
        val piece2 = ChessPiece(5, 9, ATTACKER)

        atkBoard.layoutMap = hashMapOf(
            Pair(5, 5) to king,
            Pair(5, 6) to piece1,
            Pair(5, 9) to piece2
        )

        assertTrue(atkBoard.layoutMap.containsValue(piece1))
        atkBoard.move(piece2, 5, 7)
        val captures = piece2.capturePositionsAndPieces(5, 7, atkBoard.layoutMap)
        assertTrue(captures.isEmpty())
        assertTrue(atkBoard.layoutMap.containsValue(piece1))
    }

    @Test
    fun capture_flankedByEdgeShouldFail() {
        val piece1 = ChessPiece(0, 1, DEFENDER)
        val piece2 = ChessPiece(6, 1, ATTACKER)

        atkBoard.layoutMap = hashMapOf(
            Pair(0, 1) to piece1,
            Pair(6, 1) to piece2
        )

        assertTrue(atkBoard.layoutMap.containsValue(piece1))
        atkBoard.move(piece2, 1, 1)
        val captures = piece2.capturePositionsAndPieces(1, 1, atkBoard.layoutMap)
        assertTrue(captures.isEmpty())
        assertTrue(atkBoard.layoutMap.containsValue(piece1))
    }

    @Test
    fun capture_attackersSurroundKingShouldSucceed() {
        val king = KingPiece(2, 3, KING)
        val piece1 = ChessPiece(1, 3, ATTACKER)
        val piece2 = ChessPiece(3, 3, ATTACKER)
        val piece3 = ChessPiece(2, 2, ATTACKER)
        val piece4 = ChessPiece(2, 10, ATTACKER)

        atkBoard.layoutMap = hashMapOf(
            Pair(2, 3) to king,
            Pair(1, 3) to piece1,
            Pair(3, 3) to piece2,
            Pair(2, 2) to piece3,
            Pair(2, 10) to piece4
        )

        assertTrue(atkBoard.layoutMap.containsValue(king))
        atkBoard.move(piece4, 2, 4)
        val captures = piece4.capturePositionsAndPieces(2, 4, atkBoard.layoutMap)
        assertTrue(captures.size == 1)
        assertTrue(captures.containsValue(king))
        assertFalse(atkBoard.layoutMap.containsValue(king))
    }

    @Test
    fun capture_kingSurroundedOnEdgeLastPieceShouldSucceed() {
        val king = KingPiece(0, 5, KING)
        val piece1 = ChessPiece(4, 4, ATTACKER)
        val piece2 = ChessPiece(0, 6, ATTACKER)
        val piece3 = ChessPiece(1, 5, ATTACKER)

        atkBoard.layoutMap = hashMapOf(
            Pair(0, 5) to king,
            Pair(4, 4) to piece1,
            Pair(0, 6) to piece2,
            Pair(1, 5) to piece3
        )

        assertTrue(atkBoard.layoutMap.containsValue(king))
        atkBoard.move(piece1, 0, 4)
        val captures = piece1.capturePositionsAndPieces(0, 4, atkBoard.layoutMap)
        assertTrue(captures.size == 1)
        assertTrue(captures.containsValue(king))
        assertFalse(atkBoard.layoutMap.containsValue(king))
    }

    @Test
    fun capture_kingSurroundedOnEdgeNotLastPieceShouldFail() {
        val king = KingPiece(0, 5, KING)
        val piece1 = ChessPiece(4, 4, ATTACKER)
        val piece2 = ChessPiece(0, 6, ATTACKER)
        val piece3 = ChessPiece(1, 5, ATTACKER)
        val defender = ChessPiece(8, 8, DEFENDER)

        atkBoard.layoutMap = hashMapOf(
            Pair(0, 5) to king,
            Pair(4, 4) to piece1,
            Pair(0, 6) to piece2,
            Pair(1, 5) to piece3,
            Pair(8, 8) to defender
        )

        assertTrue(atkBoard.layoutMap.containsValue(king))
        atkBoard.move(piece1, 0, 4)
        val captures = piece1.capturePositionsAndPieces(0, 4, atkBoard.layoutMap)
        assertTrue(captures.isEmpty())
        assertTrue(atkBoard.layoutMap.containsValue(king))
    }

    @Test
    fun capture_notOnMovementShouldFail() {
        val piece1 = ChessPiece(0, 3, ATTACKER)
        val piece2 = ChessPiece(1, 9, DEFENDER)
        val piece3 = ChessPiece(2, 3, ATTACKER)

        defBoard.layoutMap = hashMapOf(
            Pair(0, 3) to piece1,
            Pair(1, 9) to piece2,
            Pair(2, 3) to piece3
        )

        assertTrue(defBoard.layoutMap.containsValue(piece2))
        defBoard.move(piece2, 1, 3)
        val captures = piece2.capturePositionsAndPieces(1, 3, defBoard.layoutMap)
        assertTrue(captures.isEmpty())
        assertTrue(defBoard.layoutMap.containsValue(piece2))
    }
}