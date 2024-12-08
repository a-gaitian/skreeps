package io.github.skreeps.api.extended

import io.github.skreeps.api.constants.Find
import io.github.skreeps.api.constants.Orientation
import io.github.skreeps.api.utils.asCodeEnum

sealed class Direction(val orientationCode: Int) {
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

    fun asOrientation(): Orientation =
        this.orientationCode.asCodeEnum()
}

sealed class Direction4(directionCode: Int, val findCode: Int): Direction(directionCode){
    companion object {
        val list = listOf(North, East, South, West)
    }
    fun asFind(): Find =
        findCode.asCodeEnum()
}

sealed class Direction8(directionCode: Int) : Direction(directionCode) {
    companion object {
        val list = listOf(NorthEast, SouthEast, SouthWest, NorthWest)
    }
}

fun Find.asDirection(): Direction4 =
    when (this) {
        Find.FIND_EXIT_TOP -> Direction.North
        Find.FIND_EXIT_RIGHT -> Direction.East
        Find.FIND_EXIT_BOTTOM -> Direction.South
        Find.FIND_EXIT_LEFT -> Direction.West
        else -> throw IllegalArgumentException("Cannot convert find code $this to direction")
    }

fun Orientation.asDirection(): Direction =
    when (this) {
        Orientation.TOP -> Direction.North
        Orientation.TOP_RIGHT -> Direction.NorthEast
        Orientation.RIGHT -> Direction.East
        Orientation.BOTTOM_RIGHT -> Direction.SouthEast
        Orientation.BOTTOM -> Direction.South
        Orientation.BOTTOM_LEFT -> Direction.SouthWest
        Orientation.LEFT -> Direction.West
        Orientation.TOP_LEFT -> Direction.NorthWest
    }
