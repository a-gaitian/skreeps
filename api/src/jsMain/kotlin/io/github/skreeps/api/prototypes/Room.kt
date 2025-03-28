package io.github.skreeps.api.prototypes

external class Room {

    class Terrain {

    }
}

typealias  RoomStatus = String

object RoomStatuses {
    /**
     * The room has no restrictions
     */
    val Normal = "normal"

    /**
     * The room is not available
     */
    val Closed = "closed"

    /**
     * The room is part of a novice area
     */
    val Novice = "novice"

    /**
     * The room is part of a respawn area
     */
    val Respawn = "respawn"
}
