@file:OptIn(ExperimentalJsExport::class)

package io.github.skreeps.api.prototypes

import io.github.skreeps.api.global.PathFinder

/**
 * Container for custom navigation cost data. By default [PathFinder] will only consider terrain data
 * (plain, swamp, wall) — if you need to route around obstacles such as buildings or creeps you must
 * put them into a [CostMatrix]. Generally you will create your [CostMatrix] from within `roomCallback`.
 * If a non-0 value is found in a room's CostMatrix then that value will be used instead of the
 * default terrain cost. You should avoid using large values in your CostMatrix and terrain cost
 * flags. For example, running [PathFinder.search] with `{ plainCost: 1, swampCost: 5 }` is faster than
 * running it with `{plainCost: 2, swampCost: 10 }` even though your paths will be the same
 *
 * @constructor Creates a new CostMatrix containing 0's for all positions
 */
@JsName("PathFinder.CostMatrix")
external class CostMatrix {
    companion object {
        /**
         * Static method which deserializes a new CostMatrix using the return value of [serialize]
         *
         * @param val Whatever [serialize] returned
         * @return A new CostMatrix instance
         */
        fun deserialize(`val`: String): CostMatrix
    }

    /**
     * Set the cost of a position in this CostMatrix
     *
     * @param x X position in the room
     * @param y Y position in the room
     * @param cost Cost of this position. Must be a whole number. A cost of 0 will use the terrain
     * cost for that tile. A cost greater than or equal to 255 will be treated as unwalkable
     */
    fun set(x: Number, y: Number, cost: Int)

    /**
     * Get the cost of a position in this CostMatrix
     *
     * @param x X position in the room
     * @param y Y position in the room
     */
    fun get(x: Number, y: Number): Int

    /**
     * Copy this CostMatrix into a new CostMatrix with the same data
     *
     * @return A new CostMatrix instance
     */
    fun clone(): CostMatrix

    /**
     * Returns a compact representation of this CostMatrix which can be stored via [JSON.stringify]
     *
     * @return An array of numbers. There's not much you can do with the numbers besides store them for later
     */
    fun serialize(): String
}
