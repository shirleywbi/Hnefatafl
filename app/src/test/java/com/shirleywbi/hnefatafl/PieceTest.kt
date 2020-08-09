package com.shirleywbi.hnefatafl

import org.junit.Before
import org.junit.Test

class PieceTest {

    @Before
    fun init() {}

    // Capture Tests

    @Test
    fun capture_attackerFlanksDefenderShouldSucceed() {}

    @Test
    fun capture_defenderFlanksAttackerShouldSucceed() {}

    @Test
    fun capture_attackersOnlyShouldFail() {}

    @Test
    fun capture_defendersOnlyShouldFail() {}

    @Test
    fun capture_defenderDefenderAttackerShouldFail() {}

    @Test
    fun capture_attackerAttackerDefenderShouldFail() {}

    @Test
    fun capture_attackersSurroundKingShouldSucceed() {}

    @Test
    fun capture_attackersFlankKingShouldFail() {}

    @Test
    fun capture_kingDefenderFlankAttackerShouldSucceed() {}

    @Test
    fun capture_kingSurroundedOnEdgeLastPieceShouldSucceed() {}

    @Test
    fun capture_kingSurroundedOnEdgeNotLastPieceShouldFail() {}

    @Test
    fun capture_kingDefenderFlankDefenderShouldFail() {}

    @Test
    fun capture_kingAttackerFlankDefenderShouldFail() {}

    @Test
    fun capture_kingAttackerFlankAttackerShouldFail() {}

    @Test
    fun capture_notOnMovementShouldFail() {}
}