package io.github.skreeps.api.prototypes

import io.github.skreeps.api.constants.ErrorCode
import io.github.skreeps.api.constants.ErrorCodes
import io.github.skreeps.api.constants.StructureType
import io.github.skreeps.api.constants.StructureTypes
import io.github.skreeps.api.global.Game

/**
 * A site of a structure which is currently under construction. A construction site can be
 * created using the 'Construct' button at the left of the game field
 * or the [Room.createConstructionSite] method.
 *
 * To build a structure on the construction site, give a worker creep some amount of
 * energy and perform [Creep.build] action.
 *
 * You can remove enemy construction sites by moving a creep on it
 */
external class ConstructionSite {

    /**
     * A unique object identificator. You can use [Game.getObjectById] method to retrieve an object instance by its [id]
     */
    val id: String

    /**
     * Whether this is your own construction site
     */
    val my: Boolean

    /**
     * An object with the structure’s owner info
     */
    val owner: Owner

    /**
     * The current construction progress
     */
    val progress: Number

    /**
     * The total construction progress needed for the structure to be built
     */
    val progressTotal: Number

    /**
     * One of the [StructureTypes] constants
     */
    val structureType: StructureType

    /**
     * Remove the construction site
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this construction site, and it's not in your room
     *
     */
    fun remove(): ErrorCode
}