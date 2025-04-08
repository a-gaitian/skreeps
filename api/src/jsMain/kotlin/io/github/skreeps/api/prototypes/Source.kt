package io.github.skreeps.api.prototypes

import io.github.skreeps.api.constants.BodyParts
import io.github.skreeps.api.global.Game

/**
 * An energy source object. Can be harvested by creeps with a [BodyParts.Work] body part
 */
external class Source : RoomObject {

    /**
     * The remaining amount of energy
     */
    val energy: Number

    /**
     * The total amount of energy in the source
     */
    val energyCapacity: Number

    /**
     * A unique object identificator.
     * You can use [Game.getObjectById] method to retrieve an object instance by its [id]
     */
    val id: String

    /**
     * The remaining time after which the source will be refilled
     */
    val ticksToRegeneration: Number
}