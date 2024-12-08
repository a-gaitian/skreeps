package io.github.skreeps.api

import io.github.skreeps.api.constant.DirectionCode
import io.github.skreeps.api.constant.FindCode

sealed class Direction(val directionCode: Int) {
    companion object {
        val list = listOf(North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest)
    }

    data object North : Direction4(1, 1)
    data object NorthEast : Direction8(2)
    data object East : Direction4(2, 3)
    data object SouthEast : Direction8(4)
    data object South : Direction4(3, 5)
    data object SouthWest : Direction8(6)
    data object West : Direction4(4, 7)
    data object NorthWest : Direction8(8)

    fun asDirectionCode(): DirectionCode =
        DirectionCode.valueOf(this.directionCode)
}

sealed class Direction4(directionCode: Int, val findCode: Int): Direction(directionCode){
    companion object {
        val list = listOf(North, East, South, West)
    }
    fun asFindCode(): FindCode =
        FindCode.valueOf(this.findCode)
}

sealed class Direction8(directionCode: Int) : Direction(directionCode) {
    companion object {
        val list = listOf(NorthEast, SouthEast, SouthWest, NorthWest)
    }
}

fun FindCode.asDirection(): Direction =
    when (this) {
        FindCode.FIND_EXIT_TOP -> Direction.North
        FindCode.FIND_EXIT_RIGHT -> Direction.East
        FindCode.FIND_EXIT_BOTTOM -> Direction.South
        FindCode.FIND_EXIT_LEFT -> Direction.West
        else -> throw IllegalArgumentException("Cannot convert find code $this to direction")
    }

fun DirectionCode.asDirection(): Direction =
    when (this) {
        DirectionCode.TOP -> Direction.North
        DirectionCode.TOP_RIGHT -> Direction.NorthEast
        DirectionCode.RIGHT -> Direction.East
        DirectionCode.BOTTOM_RIGHT -> Direction.SouthEast
        DirectionCode.BOTTOM -> Direction.South
        DirectionCode.BOTTOM_LEFT -> Direction.SouthWest
        DirectionCode.LEFT -> Direction.West
        DirectionCode.TOP_LEFT -> Direction.NorthWest
    }
