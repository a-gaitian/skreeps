@file:OptIn(ExperimentalJsCollectionsApi::class)

package io.github.skreeps.api.global

import io.github.skreeps.api.prototypes.*
import io.github.skreeps.api.utils.ResultMap

/**
 * The main global game object containing all the game play information
 */
external object Game {

    /**
     * A hash containing all your construction sites with their id as hash keys
     */
    val constructionSites: ResultMap<ConstructionSite>

    /**
     * An object containing information about your CPU usage
     */
    val cpu: Cpu

    /**
     * A hash containing all your creeps with creep names as hash keys
     */
    val creeps: ResultMap<Creep>

    /**
     * A hash containing all your flags with flag names as hash keys
     */
    val flags: ResultMap<Flag>

    /**
     * Your [Global Control Level](https://docs.screeps.com/control.html#Global-Control-Level)
     */
    val gcl: GlobalLevel

    /**
     * Your [Global Power Level](https://docs.screeps.com/power.html#Global-Power-Level)
     */
    val gpl: GlobalLevel

    class GlobalLevel {

        /**
         * The current level
         */
        val level: Number

        /**
         * The current progress to the next level
         */
        val progress: Number

        /**
         * The progress required to reach the next level
         */
        val progressTotal: Number
    }

    /**
     * A global object representing world map
     */
    val map: GameMap

    /**
     * A global object representing the in-game market
     */
    val market: Market

    /**
     * A hash containing all your power creeps with their names as hash keys.
     * Even power creeps not spawned in the world can be accessed here
     */
    val powerCreeps: ResultMap<PowerCreep>

    /**
     * An object with your global resources that are bound to the account, like pixels or cpu unlocks.
     * Each object key is a resource constant, values are resources amounts
     */
    val resources: ResultMap<Number>

    /**
     * A hash containing all the rooms available to you with room names as hash keys.
     * A room is visible if you have a creep or an owned structure in it
     */
    val rooms: ResultMap<Room>

    /**
     * An object describing the world shard where your script is currently being executed in
     */
    val shard: Shard

    class Shard {

        /**
         * The name of the shard
         */
        val name: String

        /**
         * Currently always equals to _normal_
         */
        val type: String

        /**
         * Whether this shard belongs to the [PTR](https://docs.screeps.com/ptr.html)
         */
        val ptr: Boolean // NPE with JsBoolean
    }

    /**
     * A hash containing all your spawns with spawn names as hash keys
     */
    val spawns: ResultMap<StructureSpawn>

    /**
     * A hash containing all your structures with structure id as hash keys
     */
    val structures: ResultMap<Structure>

    /**
     * System game tick counter. It is automatically incremented on every tick.
     * [Learn more](https://docs.screeps.com/game-loop.html)
     */
    val time: Number

    /**
     * Get an object with the specified unique ID. It may be a game object of any type.
     * Only objects from the rooms which are visible to you can be accessed
     *
     * @param id the unique identificator
     *
     * @return an object instance or null if it cannot be found
     */
    fun <T> getObjectById(id: String): T?

    /**
     * Send a custom message at your profile email. This way, you can set up notifications
     * to yourself on any occasion within the game. You can schedule up to 20 notifications
     * during one game tick. Not available in the Simulation Room
     *
     * @param message custom text which will be sent in the message. Maximum length is 1000 characters
     *
     * @param groupInterval if set to 0 (default), the notification will be scheduled immediately.
     * Otherwise, it will be grouped with other notifications and mailed out later using
     * the specified time in minutes
     */
    fun notify(message: String, groupInterval: Number? = definedExternally)
}
