package io.github.skreeps.api.prototypes

/**
 * Any object with a position in a room. Almost all game objects prototypes are derived from [RoomObject]
 */
abstract external class RoomObject {

    /**
     * Applied effects
     */
    val effects: Array<Effect>

    /**
     * An object representing the position of this object in the room
     */
    val pos: RoomPosition

    /**
     * The link to the Room object. May be undefined in case if an object is a flag or
     * a construction site and is placed in a room that is not visible to you
     */
    val room: Room?
}

external class Effect {

    /**
     * Effect ID of the applied effect. Can be either natural effect ID or Power ID
     */
    val effect: Number

    /**
     * How many ticks will the effect last
     */
    val ticksRemaining: Number

    /**
     * Power level of the applied effect. Absent if the effect is not a Power effect
     */
    val level: Number?
}
