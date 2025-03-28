@file:Suppress("NON_EXPORTABLE_TYPE")
@file:OptIn(ExperimentalJsExport::class)

package io.github.skreeps.api.global

import io.github.skreeps.api.prototypes.CostMatrix
import io.github.skreeps.api.prototypes.RoomPosition

/**
 * Contains powerful methods for pathfinding in the game world. This module is written in fast
 * native C++ code and supports custom navigation costs and paths which span multiple rooms
 */
external object PathFinder {

    /**
     * Find an optimal path between [origin] and [goal]
     *
     * @param origin The start position
     * @param goal A goal or an array of goals. If more than one goal is supplied then the cheapest
     * path found out of all the goals will be returned. A goal is either
     * a [RoomPosition] or an [PathFinderGoal].
     *
     * **_Important_**: Please note that if your goal is not walkable (for instance, a source) then you
     * should set range to at least 1 or else you will waste many CPU cycles searching for a target
     * hat you can't walk on
     *
     * @param opts An object containing additional pathfinding flags
     */
    fun search(origin: RoomPosition, goal: PathFinderGoal, opts: PathFinderOptions? = definedExternally): PathFinderResult

    /**
     * Find an optimal path between [origin] and [goal]
     *
     * @param origin The start position
     * @param goal A goal or an array of goals. If more than one goal is supplied then the cheapest
     * path found out of all the goals will be returned. A goal is either
     * a [RoomPosition] or an [PathFinderGoal].
     *
     * **_Important_**: Please note that if your goal is not walkable (for instance, a source) then you
     * should set range to at least 1 or else you will waste many CPU cycles searching for a target
     * hat you can't walk on
     *
     * @param opts An object containing additional pathfinding flags
     */
    fun search(origin: RoomPosition, goal: Array<PathFinderGoal>, opts: PathFinderOptions? = definedExternally): PathFinderResult

    /**
     * Find an optimal path between [origin] and [goal]
     *
     * @param origin The start position
     * @param goal A goal or an array of goals. If more than one goal is supplied then the cheapest
     * path found out of all the goals will be returned. A goal is either
     * a [RoomPosition] or an [PathFinderGoal].
     *
     * **_Important_**: Please note that if your goal is not walkable (for instance, a source) then you
     * should set range to at least 1 or else you will waste many CPU cycles searching for a target
     * hat you can't walk on
     *
     * @param opts An object containing additional pathfinding flags
     */
    fun search(origin: RoomPosition, goal: RoomPosition, opts: PathFinderOptions? = definedExternally): PathFinderResult

    /**
     * Find an optimal path between [origin] and [goal]
     *
     * @param origin The start position
     * @param goal A goal or an array of goals. If more than one goal is supplied then the cheapest
     * path found out of all the goals will be returned. A goal is either
     * a [RoomPosition] or an [PathFinderGoal].
     *
     * **_Important_**: Please note that if your goal is not walkable (for instance, a source) then you
     * should set range to at least 1 or else you will waste many CPU cycles searching for a target
     * hat you can't walk on
     *
     * @param opts An object containing additional pathfinding flags
     */
    fun search(origin: RoomPosition, goal: Array<RoomPosition>, opts: PathFinderOptions? = definedExternally): PathFinderResult
}

@JsExport
data class PathFinderGoal(
    /**
     * The target
     */
    val pos: RoomPosition,

    /**
     * Range to pos before goal is considered reached
     */
    val range: Number = 0
)

@JsExport
data class PathFinderOptions(

    /**
     * Cost for walking on plain positions
     */
    val plainCost: Number = 1,

    /**
     * Cost for walking on swamp positions
     */
    val swampCost: Number = 5,

    /**
     * Instead of searching for a path to the goals this will search for a path _away_ from the goals.
     * The cheapest path that is out of `range` of every goal will be returned
     */
    val flee: Boolean = false,

    /**
     * The maximum allowed pathfinding operations.
     * You can limit CPU time used for the search based on ratio 1 op ~ 0.001 CPU
     */
    val maxOps: Number = 2000,

    /**
     * The maximum allowed rooms to search. Maximum is 64
     */
    val maxRooms: Number = 16,

    /**
     * The maximum allowed cost of the path returned. If at any point the pathfinder detects that
     * it is impossible to find a path with a cost less than or equal to [maxCost] it will
     * immediately halt the search
     */
    val maxCost: Number = Double.POSITIVE_INFINITY,

    /**
     * Weight to apply to the heuristic in the A* formula `F = G + weight * H`.
     * Use this option only if you understand the underlying A* algorithm mechanics!
     */
    val heuristicWeight: Number = 1.2,

    /**
     * Request from the pathfinder to generate a [CostMatrix] for a certain room. The callback accepts
     * one argument, `roomName`. This callback will only be called once per room per search. If you
     * are running multiple pathfinding operations in a single room and in a single tick you may
     * consider caching your CostMatrix to speed up your code. Please read the CostMatrix
     * documentation below for more information on CostMatrix. If you return `false` from the callback
     * the requested room will not be searched, and it won't count against [maxRooms]
     */
    val roomCallback: (roomName: String) -> CostMatrix? = { null }
)

external class PathFinderResult {

    /**
     * An array of RoomPosition objects
     */
    val path: Array<RoomPosition>

    /**
     * Total number of operations performed before this path was calculated
     */
    val ops: Number

    /**
     * The total cost of the path as derived from `plainCost`, `swampCost` and any given
     * CostMatrix instances
     */
    val cost: Number

    /**
     * If the pathfinder fails to find a complete path, this will be true. Note that `path` will
     * still be populated with a partial path which represents the closest path it could find
     * given the search parameters
     */
    val incomplete: Boolean
}
