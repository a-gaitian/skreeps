package io.github.skreeps.api.prototypes

import io.github.skreeps.api.constants.ErrorCode
import io.github.skreeps.api.constants.StructureType
import io.github.skreeps.api.global.Game

/**
 * The base prototype object of all structures
 */
abstract external class Structure: RoomObject {

    /**
     * The current amount of hit points of the structure
     */
    val hits: Number

    /**
     * The total amount of hit points of the structure
     */
    val hitsMax: Number

    /**
     * A unique object identificator. You can use [Game.getObjectById] method
     * to retrieve an object instance by its [id]
     */
    val id: String

    /**
     * One of the [StructureType] constants
     */
    val structureType: StructureType

    /**
     * Destroy this structure immediately
     *
     * @return One of the following codes:
     *
     * [Ok] - The operation has been scheduled successfully
     *
     * [NotOwner] - You are not the owner of this structure, and it's not in your room
     *
     * [Busy] - Hostile creeps are in the room
     */
    fun destroy(): ErrorCode

    /**
     * Check whether this structure can be used. If room controller level is insufficient,
     * then this method will return false, and the structure will be highlighted with red in the game
     */
    fun isActive(): Boolean

    /**
     * Toggle auto notification when the structure is under attack. The notification will be
     * sent to your account email. Turned on by default
     *
     * @param enabled Whether to enable notification or disable
     *
     * @return One of the following codes:
     *
     * [Ok] - The operation has been scheduled successfully
     *
     * [NotOwner] - You are not the owner of this structure
     *
     * [InvalidArgs] - `enable` argument is not a boolean value
     */
    fun notifyWhenAttacked(enabled: Boolean): ErrorCode
}