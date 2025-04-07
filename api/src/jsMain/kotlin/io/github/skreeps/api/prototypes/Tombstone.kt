package io.github.skreeps.api.prototypes

import io.github.skreeps.api.global.Game

/**
 * A remnant of dead creeps. This is a walkable object
 *
 * **Decay:** 5 ticks per body part of the deceased creep
 */
external class Tombstone : RoomObject {

    /**
     * An object containing the deceased creep or power creep
     */
    val creep: CreepTarget

    /**
     * Time of death
     */
    val deathTime: Number

    /**
     * A unique object identificator. You can use [Game.getObjectById] method to retrieve an object instance by its [id]
     */
    val id : String

    /**
     * A [Store] object that contains cargo of this structure
     */
    val store: Store

    /**
     * The amount of game ticks before this tombstone decays
     */
    val ticksToDecay: Number
}