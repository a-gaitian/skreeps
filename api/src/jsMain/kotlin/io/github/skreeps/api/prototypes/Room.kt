package io.github.skreeps.api.prototypes

external class Room {

    class Terrain {

    }
}

enum class RoomStatus {
    /**
     * The room has no restrictions
     */
    Normal,

    /**
     * The room is not available
     */
    Closed,

    /**
     * The room is part of a novice area
     */
    Novice,

    /**
     * The room is part of a respawn area
     */
    Respawn
}
