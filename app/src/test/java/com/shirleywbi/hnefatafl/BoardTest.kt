package com.shirleywbi.hnefatafl

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BoardTest {

    lateinit var board: Board

    @Before
    fun init() {
        board = Board()
    }

    @Test
    fun boardLayoutIsCorrect() {

    }

    @Test
    fun chessPiecesAreCorrect() {

    }

    @Test
    fun canMove() {

    }

    @Test
    fun canMove_blockedShouldFail() {

    }

    @Test
    fun canMove_normalToRestrictedShouldFail() {

    }

    @Test
    fun canMove_kingToRestrictedShouldSucceed() {

    }
}