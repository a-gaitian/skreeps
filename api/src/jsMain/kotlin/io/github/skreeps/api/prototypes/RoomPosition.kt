package io.github.skreeps.api.prototypes

/**
 * An object representing the specified position in the room.
 * Every [RoomObject] in the room contains [RoomPosition] as the [pos] property.
 * The position object of a custom location can be obtained using the [Room.getPositionAt] method or using the constructor
 */
external class RoomPosition(

    /**
     * X position in the room
     */
    val x: Number,

    /**
     * Y position in the room
     */
    val y: Number,

    /**
     * The name of the room
     */
    val roomName: String
)