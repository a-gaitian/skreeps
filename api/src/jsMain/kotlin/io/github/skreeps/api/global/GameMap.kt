@file:OptIn(ExperimentalJsCollectionsApi::class)

package io.github.skreeps.api.global

import io.github.skreeps.api.*
import io.github.skreeps.api.constant.FindResultCode
import io.github.skreeps.api.prototypes.Room
import kotlin.js.collections.JsArray

/**
 * A global object representing world map. Use it to navigate between rooms
 */
external class GameMap {

    /**
     * List all exits available from the room with the given name
     *
     * @param roomName the room name
     *
     * @return The exits information, or null if the room not found
     */
    fun describeExits(roomName: String): Exits?

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
    fun findExit(fromRoom: Room, toRoom: Room, opts: RouteOpts? = definedExternally): Result<FindResultCode>
    /**
     * @see findExit
     */
    fun findExit(fromRoom: String, toRoom: String, opts: RouteOpts? = definedExternally): Result<FindResultCode>

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
    fun findRoute(fromRoom: Room, toRoom: Room, opts: RouteOpts? = definedExternally): Result<JsArray<Any>>
    /**
     * @see findRoute
     */
    fun findRoute(fromRoom: String, toRoom: String, opts: RouteOpts? = definedExternally): Result<JsArray<Any>>

    class RouteEntry {
        val exit: Int
        val room: String
    }
}

/**
 * @see GameMap.describeExits
 */
external class Exits

fun Exits?.getRoomNameIn(direction: Direction4): String? = this?.run {
    asDynamic()[direction.findCode] as String?
}

fun Exits?.toMap() = this?.run {
    buildMap {
        Direction4.list.forEach {
            this@run.getRoomNameIn(it)?.let { roomName ->
                put(it, roomName)
            }
        }
    }
} ?: emptyMap()

/**
 * @see RouteOpts.routeCallback
 */
external interface RouteOpts {
    /**
     * This callback can be used to calculate the cost of entering that room.
     * You can use this to do things like prioritize your own rooms, or avoid some rooms.
     * You can return a floating point cost or _Infinity_ to block the room
     */
    var routeCallback: (roomName: String, fromRoomName: String) -> Number
}

/**
 * @see RouteOpts.routeCallback
 */
fun routeOpts(routeCallback: (roomName: String, fromRoomName: String) -> Number) =
    object: RouteOpts { override var routeCallback = routeCallback }