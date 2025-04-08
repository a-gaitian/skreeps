@file:OptIn(ExperimentalJsExport::class, ExperimentalUnsignedTypes::class)
@file:Suppress("INLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING", "NON_EXPORTABLE_TYPE")

package io.github.skreeps.api.prototypes

import io.github.skreeps.api.constants.*
import io.github.skreeps.api.global.Color
import io.github.skreeps.api.global.Game
import io.github.skreeps.api.global.MemoryModel
import io.github.skreeps.api.global.PathFinder
import io.github.skreeps.api.utils.Result

/**
 * An object representing the room in which your units and structures are in.
 * It can be used to look around, find paths, etc. Every [RoomObject] in the room contains
 * its linked [Room] instance in the [room][RoomObject.room] property
 */
external class Room {

    /**
     * The Controller structure of this room, if present, otherwise undefined
     */
    val controller: StructureController?

    /**
     * Total amount of energy available in all spawns and extensions in the room
     */
    val energyAvailable: Number

    /**
     * Total amount of `energyCapacity` of all spawns and extensions in the room
     */
    val energyCapacityAvailable: Number

    /**
     * A shorthand to `Memory.rooms[room.name]`. You can use it for quick access the room’s specific memory data object
     */
    val memory: MemoryModel

    /**
     * The name of the room
     */
    val name: String

    /**
     * The Storage structure of this room, if present, otherwise undefined
     */
    val storage: StructureStorage?

    /**
     * The Terminal structure of this room, if present, otherwise undefined
     */
    val terminal: StructureTerminal?

    /**
     * A [RoomVisual] object for this room. You can use this object to draw simple shapes (lines, circles, text labels) in the room
     */
    val visual: RoomVisual

    /**
     * Serialize a path array into a short string representation, which is suitable to store in memory
     *
     * @param path A path array retrieved from [Room.findPath]
     *
     * @returns A serialized string form of the given path
     */
    fun serializePath(path: Array<PathStep>): String

    /**
     * Deserialize a short string path representation into an array form
     *
     * @param path A serialized path string
     *
     * @returns A path array
     */
    fun deserializePath(path: String): Array<PathStep>

    /**
     * Create new [ConstructionSite] at the specified location
     *
     * @param x The X position
     * @param y The Y position
     * @param pos Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     * @param structureType One of the [StructureTypes] constants
     * @param name The name of the structure, for structures that support it (currently only spawns). The name length limit is 100 characters
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - The room is claimed or reserved by a hostile player
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The structure cannot be placed at the specified location
     *
     * [ERR_FULL][ErrorCodes.Full] - You have too many construction sites. The maximum number of construction sites per player is 100
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The location is incorrect
     *
     * [ERR_RCL_NOT_ENOUGH][ErrorCodes.RclNotEnough] - Room Controller Level insufficient
     *
     */
    fun createConstructionSite(x: Number, y: Number, type: StructureType, name: String? = definedExternally): ErrorCode

    /**
     * Create new [ConstructionSite] at the specified location
     *
     * @param x The X position
     * @param y The Y position
     * @param pos Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     * @param structureType One of the [StructureTypes] constants
     * @param name The name of the structure, for structures that support it (currently only spawns). The name length limit is 100 characters
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - The room is claimed or reserved by a hostile player
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The structure cannot be placed at the specified location
     *
     * [ERR_FULL][ErrorCodes.Full] - You have too many construction sites. The maximum number of construction sites per player is 100
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The location is incorrect
     *
     * [ERR_RCL_NOT_ENOUGH][ErrorCodes.RclNotEnough] - Room Controller Level insufficient
     *
     */
    fun createConstructionSite(pos: RoomPosition, type: StructureType, name: String? = definedExternally): ErrorCode

    /**
     * Create new [ConstructionSite] at the specified location
     *
     * @param x The X position
     * @param y The Y position
     * @param pos Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     * @param structureType One of the [StructureTypes] constants
     * @param name The name of the structure, for structures that support it (currently only spawns). The name length limit is 100 characters
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - The room is claimed or reserved by a hostile player
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The structure cannot be placed at the specified location
     *
     * [ERR_FULL][ErrorCodes.Full] - You have too many construction sites. The maximum number of construction sites per player is 100
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The location is incorrect
     *
     * [ERR_RCL_NOT_ENOUGH][ErrorCodes.RclNotEnough] - Room Controller Level insufficient
     *
     */
    fun createConstructionSite(pos: RoomObject, type: StructureType, name: String? = definedExternally): ErrorCode

    /**
     * Create new [Flag] at the specified location
     *
     * @param x The X position
     * @param y The Y position
     * @param pos Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     * @param name The name of a new flag. It should be unique, i.e. the [Game.flags] object
     * should not contain another flag with the same name (hash key). If not defined,
     * a random name will be generated. The maximum length is 100 characters
     * @param color The color of a new flag. Should be one of the [Color] constants. The default value is [Color.White]
     * @param secondaryColor The secondary color of a new flag. Should be one of the [Color] constants. The default value is equal to [color]
     *
     * @return The name of a new flag, or one of the following error codes:
     *
     * [ERR_NAME_EXISTS][ErrorCodes.NameExists] - There is a flag with the same name already
     *
     * [ERR_FULL][ErrorCodes.Full] - You have too many flags. The maximum number of flags per player is 10000
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The location or the name or the color constant is incorrect
     *
     */
    fun createFlag(x: Number, y: Number, name: String? = definedExternally, color: Color? = definedExternally, secondaryColor: Color? = definedExternally): Result<String>

    /**
     * Create new [Flag] at the specified location
     *
     * @param x The X position
     * @param y The Y position
     * @param pos Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     * @param name The name of a new flag. It should be unique, i.e. the [Game.flags] object
     * should not contain another flag with the same name (hash key). If not defined,
     * a random name will be generated. The maximum length is 100 characters
     * @param color The color of a new flag. Should be one of the [Color] constants. The default value is [Color.White]
     * @param secondaryColor The secondary color of a new flag. Should be one of the [Color] constants. The default value is equal to [color]
     *
     * @return The name of a new flag, or one of the following error codes:
     *
     * [ERR_NAME_EXISTS][ErrorCodes.NameExists] - There is a flag with the same name already
     *
     * [ERR_FULL][ErrorCodes.Full] - You have too many flags. The maximum number of flags per player is 10000
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The location or the name or the color constant is incorrect
     *
     */
    fun createFlag(pos: RoomPosition, name: String? = definedExternally, color: Color? = definedExternally, secondaryColor: Color? = definedExternally): Result<String>

    /**
     * Create new [Flag] at the specified location
     *
     * @param x The X position
     * @param y The Y position
     * @param pos Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     * @param name The name of a new flag. It should be unique, i.e. the [Game.flags] object
     * should not contain another flag with the same name (hash key). If not defined,
     * a random name will be generated. The maximum length is 100 characters
     * @param color The color of a new flag. Should be one of the [Color] constants. The default value is [Color.White]
     * @param secondaryColor The secondary color of a new flag. Should be one of the [Color] constants. The default value is equal to [color]
     *
     * @return The name of a new flag, or one of the following error codes:
     *
     * [ERR_NAME_EXISTS][ErrorCodes.NameExists] - There is a flag with the same name already
     *
     * [ERR_FULL][ErrorCodes.Full] - You have too many flags. The maximum number of flags per player is 10000
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The location or the name or the color constant is incorrect
     *
     */
    fun createFlag(pos: RoomObject, name: String? = definedExternally, color: Color? = definedExternally, secondaryColor: Color? = definedExternally): Result<String>

    /**
     * Find all objects of the specified type in the room. Results are cached automatically
     * for the specified room and type before applying any custom filters.
     * This automatic cache lasts until the end of the tick
     *
     * @param type One of the [FindCode] constants
     * @param opts An object with additional options
     */
    fun find(type: FindCode, opts: dynamic): Array<RoomObject> // TODO Type-safe lodash filter

    /**
     * Find the exit direction en route to another room. Please note that this method
     * is not required for inter-room movement, you can simply pass the target
     * in another room into [Creep.moveTo] method
     *
     * @param room Another room name or room object
     *
     * @return One of the [FindCodes] exit constants:
     *  - [FindCodes.ExitTop]
     *  - [FindCodes.ExitRight]
     *  - [FindCodes.ExitBottom]
     *  - [FindCodes.ExitLeft]
     *
     *  Or one of the following error codes:
     *
     * [ERR_NO_PATH][ErrorCodes.NoPath] - Path cannot be found
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The location is incorrect
     */
    fun findExitTo(room: String): Result<FindCode>

    /**
     * Find the exit direction en route to another room. Please note that this method
     * is not required for inter-room movement, you can simply pass the target
     * in another room into [Creep.moveTo] method
     *
     * @param room Another room name or room object
     *
     * @return One of the [FindCodes] exit constants:
     *  - [FindCodes.ExitTop]
     *  - [FindCodes.ExitRight]
     *  - [FindCodes.ExitBottom]
     *  - [FindCodes.ExitLeft]
     *
     *  Or one of the following error codes:
     *
     * [ERR_NO_PATH][ErrorCodes.NoPath] - Path cannot be found
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The location is incorrect
     */
    fun findExitTo(room: Room): Result<FindCode>

    /**
     * Find an optimal path inside the room between fromPos and toPos using
     * [Jump Point Search algorithm](http://en.wikipedia.org/wiki/Jump_point_search)
     *
     * @param fromPos The start position
     * @param toPos The end position
     * @param opts An object containing additional pathfinding flags
     */
    fun findPath(fromPos: RoomPosition, toPos: RoomPosition, opts: RoomFindPathOpts): Array<PathStep>

    /**
     * Returns an array of events happened on the previous tick in this room
     *
     * @param raw If this parameter is false or undefined, the method returns an object
     * parsed using [JSON.parse] which incurs some CPU cost on the first access
     * (the return value is cached on subsequent calls).
     * If [raw] is truthy, then raw JSON in string format is returned
     */
    fun getEventLog(raw: Boolean = definedExternally): Array<Event>

    /**
     * Creates a [RoomPosition] object at the specified location
     */
    fun getPositionAt(x: Number, y: Number): RoomPosition?

    /**
     * Get a [Room.Terrain] object which provides fast access to static terrain data.
     * This method works for any room in the world even if you have no access to it
     *
     * @return new [Room.Terrain] object
     */
    fun getTerrain(): Terrain

    /**
     * Get the list of objects at the specified room position
     *
     * @param x The X position
     * @param y The Y position
     * @param target Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     */
    fun lookAt(x: Number, y: Number): Array<dynamic>

    /**
     * Get the list of objects at the specified room position
     *
     * @param x The X position
     * @param y The Y position
     * @param target Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     */
    fun lookAt(target: RoomPosition): Array<dynamic> // TODO Type-safe lookAt result

    /**
     * Get the list of objects at the specified room position
     *
     * @param x The X position
     * @param y The Y position
     * @param target Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     */
    fun lookAt(target: RoomObject): Array<dynamic>

    /**
     * Get the list of objects at the specified room area
     *
     * @param top The top Y boundary of the area
     * @param left The left X boundary of the area
     * @param bottom The bottom Y boundary of the area
     * @param right The right X boundary of the area
     * @param asArray Set to true if you want to get the result as a plain array
     */
    fun lookAtArea(top: Number, left: Number, bottom: Number, right: Number, asArray: Boolean = definedExternally): dynamic // TODO Type-safe lookAtArea result

    /**
     * Get an object with the given type at the specified room position
     *
     * @param type One of the [LookAt] constants
     * @param x X position in the room
     * @param y Y position in the room
     * @param target Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     */
    fun lookForAt(type: LookType, x: Number, y: Number): Array<RoomObject>

    /**
     * Get an object with the given type at the specified room position
     *
     * @param type One of the [LookAt] constants
     * @param x X position in the room
     * @param y Y position in the room
     * @param target Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     */
    fun lookForAt(type: LookType, target: RoomPosition): Array<RoomObject>

    /**
     * Get an object with the given type at the specified room position
     *
     * @param type One of the [LookAt] constants
     * @param x X position in the room
     * @param y Y position in the room
     * @param target Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject])
     */
    fun lookForAt(type: LookType, target: RoomObject): Array<RoomObject>

    /**
     * Get the list of objects with the given type at the specified room area
     *
     * @param type One of the [LookAt] constants
     * @param top The top Y boundary of the area
     * @param left The left X boundary of the area
     * @param bottom The bottom Y boundary of the area
     * @param right The right X boundary of the area
     * @param asArray Set to true if you want to get the result as a plain array
     */
    fun lookForAtArea(type: LookType, top: Number, left: Number, bottom: Number, right: Number, asArray: Boolean = definedExternally): dynamic // TODO Type-safe lookForAtArea result

    class PathStep(
        val x: Number,
        val y: Number,
        val dx: Number,
        val dy: Number,
        val direction: Direction
    )

    class Event(
        val event: EventType,
        val objectId: String,
        val data: dynamic // TODO Type-safe data
    )

    /**
     * An object which provides fast access to room terrain data. These objects can be
     * constructed for any room in the world even if you have no access to it.
     *
     * Technically every [Room.Terrain] object is a very lightweight adapter to underlying
     * static terrain buffers with corresponding minimal accessors
     *
     * @constructor Creates a new [Terrain] of room by its name. [Terrain] objects can
     * be constructed for any room in the world even if you have no access to it
     */
    class Terrain(roomName: String) {

        /**
         * et terrain type at the specified room position by `(x,y)` coordinates.
         * Unlike the `Game.map.getTerrainAt(...)`(deprecated) method, this one doesn't perform any string operations and returns integer terrain type values
         *
         * @param x X position in the room
         * @param y Y position in the room
         */
        fun get(x: Number, y: Number): TerrainType

        /**
         * Get copy of underlying static terrain buffer. Current underlying representation is `Uint8Array
         *
         * _**WARNING:** this method relies on underlying representation of terrain data.
         * This is the fastest way to obtain terrain data of the whole room (2500 tiles),
         * but users should keep in mind that it can be marked as deprecated anytime in the future,
         * or return value type can be changed due to underlying data representation changing_
         *
         * @param destinationArray A typed array view in which terrain will be copied to
         * @return Copy of underlying room terrain representations as a new Uint8Array typed array of size 2500.
         *
         * Each element is an integer number, terrain type can be obtained by applying bitwise
         * AND (&) operator with appropriate [TerrainTypes] constant. Room tiles are stored **row by row**.
         *
         * If [destinationArray] is specified, function returns reference to this filled
         * [destinationArray] if coping succeeded, or error code otherwise:
         *
         * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - destinationArray type is incompatible
         */
        fun getRawBuffer(destinationArray: UByteArray? = definedExternally): Result<UByteArray>
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

@JsExport
sealed interface FindPathOpts {
    val ignoreCreeps: Boolean
    val ignoreDestructibleStructures: Boolean
    val ignoreRoads: Boolean
    val costCallback: (roomName: String, costMatrix: CostMatrix) -> CostMatrix?
    val ignore: Array<RoomPosition>
    val avoid: Array<RoomPosition>
    val maxOps: Number
    val heuristicWeight: Number
    val serialize: Boolean
    val maxRooms: Number
    val range: Number
    val plainCost: Number
    val swampCost: Number
}

@JsExport
open class RoomFindPathOpts(

    /**
     * Treat squares with creeps as walkable. Can be useful with too many moving creeps around or in some other cases
     */
    override val ignoreCreeps: Boolean = false,

    /**
     * Treat squares with destructible structures (constructed walls, ramparts, spawns, extensions) as walkable
     */
    override val ignoreDestructibleStructures: Boolean = false,

    /**
     * Ignore road structures. Enabling this option can speed up the search.
     * This is only used when the new [PathFinder] is enabled
     */
    override val ignoreRoads: Boolean = false,

    /**
     * ou can use this callback to modify a [CostMatrix] for any room during the search.
     * The callback accepts two arguments, `roomName` and `costMatrix`.
     * Use the `costMatrix` instance to make changes to the positions costs.
     * If you return a new matrix from this callback, it will be used instead of the built-in cached one.
     * This option is only used when the new [PathFinder] is enabled
     */
    override val costCallback: (roomName: String, costMatrix: CostMatrix) -> CostMatrix? = {_, _ -> null },

    /**
     * An array of the room's objects or [RoomPosition] objects which should be treated
     * as walkable tiles during the search. This option cannot be used when
     * the new [PathFinder] is enabled (use [costCallback] option instead)
     */
    override val ignore: Array<RoomPosition> = emptyArray(),

    /**
     * An array of the room's objects or [RoomPosition] objects which should be treated
     * as obstacles during the search. This option cannot be used when
     * the new [PathFinder] is enabled (use [costCallback] option instead)
     */
    override val avoid: Array<RoomPosition> = emptyArray(),

    /**
     * The maximum limit of possible pathfinding operations.
     * You can limit CPU time used for the search based on ratio 1 op ~ 0.001 CPU
     */
    override val maxOps: Number = 2000,

    /**
     * Weight to apply to the heuristic in the A formula `F = G + weight H`.
     * Use this option only if you understand the underlying A* algorithm mechanics!
     */
    override val heuristicWeight: Number = 1.2,

    /**
     * If true, the result path will be serialized using [Room.serializePath]
     */
    override val serialize: Boolean = false,

    /**
     * The maximum allowed rooms to search. The maximum is 16.
     * This is only used when the new [PathFinder] is enabled
     */
    override val maxRooms: Number = 16,

    /**
     * Find a path to a position in specified linear range of target
     */
    override val range: Number = 0,

    /**
     * Cost for walking on plain positions
     */
    override val plainCost: Number = 1,

    /**
     * Cost for walking on swamp positions
     */
    override val swampCost: Number = 5

) : FindPathOpts
