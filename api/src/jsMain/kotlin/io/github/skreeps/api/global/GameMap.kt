@file:OptIn(ExperimentalJsCollectionsApi::class)

package io.github.skreeps.api.global

import io.github.skreeps.api.*
import io.github.skreeps.api.constants.Error.*
import io.github.skreeps.api.constants.Find
import io.github.skreeps.api.constants.Find.*
import io.github.skreeps.api.extended.Direction4
import io.github.skreeps.api.prototypes.Room
import io.github.skreeps.api.prototypes.RoomStatus
import io.github.skreeps.api.utils.*

/**
 * A global object representing world map. Use it to navigate between rooms
 */
external class GameMap {

    /**
     * Map visuals provide a way to show various visual debug info on the game map.
     * You can use the [Game.map.visual][MapVisual] object to draw simple shapes that are visible only to you.
     *
     * Map visuals are not stored in the database, their only purpose is to display something in your browser.
     * All drawings will persist for one tick and will disappear if not updated.
     * All [Game.map.visual][MapVisual] calls have no added CPU cost (their cost is natural and mostly related to simple JSON.serialize calls). However, there is a usage limit: you cannot post more than 1000 KB of serialized data.
     *
     * All draw coordinates are measured in global game coordinates ([RoomPosition][io.github.skreeps.api.prototypes.RoomPosition])
     */
    val visual: MapVisual

    /**
     * List all exits available from the room with the given name
     *
     * @param roomName the room name
     *
     * @return The exits information, or null if the room not found
     */
    fun describeExits(roomName: String): Exits?

    /**
     * @see GameMap.describeExits
     */
    class Exits

    /**
     * Find the exit direction from the given room en route to another room
     *
     * @param fromRoom start room object
     * @param toRoom finish room object
     * @param opts An object with the pathfinding options
     *
     * @return The room direction constant, one of the following:
     *
     * [FIND_EXIT_TOP]
     *
     * [FIND_EXIT_RIGHT]
     *
     * [FIND_EXIT_BOTTOM]
     *
     * [FIND_EXIT_LEFT]
     *
     * Or one of the following error codes:
     *
     * [ERR_NO_PATH] - Path can not be found
     *
     * [ERR_INVALID_ARGS] - The location is incorrect
     */
    fun findExit(fromRoom: Room, toRoom: Room, opts: RouteOpts? = definedExternally): Result<Code<Find>>
    /**
     * @see findExit
     */
    fun findExit(fromRoom: String, toRoom: String, opts: RouteOpts? = definedExternally): Result<Code<Find>>

    /**
     * Find route from the given room to another room
     *
     * @param fromRoom start room object
     * @param toRoom finish room object
     * @param opts An object with the pathfinding options
     *
     * @return The route array or one of the following error codes:
     *
     * [ERR_NO_PATH] - Path can not be found
     */
    fun findRoute(fromRoom: Room, toRoom: Room, opts: RouteOpts? = definedExternally): Result<Array<RouteNode>>
    /**
     * @see findRoute
     */
    fun findRoute(fromRoom: String, toRoom: String, opts: RouteOpts? = definedExternally): Result<Array<RouteNode>>

    /**
     * @see routeCallback
     */
    interface RouteOpts {
        /**
         * This callback can be used to calculate the cost of entering that room.
         * You can use this to do things like prioritize your own rooms, or avoid some rooms.
         * You can return a floating point cost or _Infinity_ to block the room
         */
        var routeCallback: (roomName: String, fromRoomName: String) -> Number
    }

    /**
     * @see findRoute
     */
    class RouteNode {
        val exit: Code<Find>
        val room: String
    }

    /**
     * Get the linear distance (in rooms) between two rooms. You can use this function to estimate
     * the energy cost of sending resources through terminals, or using observers and nukes
     *
     * @param roomName1 The name of the first room
     * @param roomName2 The name of the second room
     * @param continuous Whether to treat the world map continuous on borders. Set to true if you
     * want to calculate the trade or terminal send cost. Default is false
     *
     * @return A number of rooms between the given two rooms
     */
    fun getRoomLinearDistance(roomName1: String, roomName2: String, continuous: Boolean = definedExternally): Number

    /**
     * Get a [Room.Terrain] object which provides fast access to static terrain data.
     * This method works for any room in the world even if you have no access to it
     *
     * @param roomName The room name
     *
     * @return Return new [Room.Terrain] object
     */
    fun getRoomTerrain(roomName: String): Room.Terrain

    /**
     * Returns the world size as a number of rooms between world corners. For example,
     * for a world with rooms from W50N50 to E50S50 this method will return 102
     */
    fun getWorldSize(): Number

    /**
     * Gets availablity status of the room with the specified name. Learn more about starting areas
     * from [this article](https://docs.screeps.com/start-areas.html)
     */
    fun getRoomStatus(roomName: String): CurrentRoomStatus

    /**
     * @see getRoomsStatus
     */
    class CurrentRoomStatus {

        val status: StringEnum<RoomStatus>

        /**
         * Status expiration time in
         * [milliseconds since UNIX epoch time](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date/getTime#Syntax).
         * This property is null if the status is permanent
         */
        val timestamp: Timestamp?
    }
}

fun GameMap.Exits?.getRoomNameIn(direction: Direction4): String? = this?.run {
    asDynamic()[direction.findCode] as String?
}

fun GameMap.Exits?.toMap() = this?.run {
    buildMap {
        Direction4.list.forEach {
            this@run.getRoomNameIn(it)?.let { roomName ->
                put(it, roomName)
            }
        }
    }
} ?: emptyMap()

fun routeOpts(routeCallback: (roomName: String, fromRoomName: String) -> Number) =
    object: GameMap.RouteOpts { override var routeCallback = routeCallback }
