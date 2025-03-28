@file:OptIn(ExperimentalJsExport::class)
@file:Suppress("NON_EXPORTABLE_TYPE")

package io.github.skreeps.api.prototypes

import io.github.skreeps.api.constants.BodyPart
import io.github.skreeps.api.constants.Direction
import io.github.skreeps.api.constants.ErrorCode
import io.github.skreeps.api.global.Game
import io.github.skreeps.api.global.MemoryModel

/**
 * Spawn is your colony center. This structure can create, renew, and recycle creeps.
 * All your spawns are accessible through [Game.spawns] hash list. Spawns auto-regenerate a little
 * amount of energy each tick, so that you can easily recover even if all your creeps died
 *
 * | Controller Level         |                                                                                                           |
 * |--------------------------|-----------------------------------------------------------------------------------------------------------|
 * | 1-6                      | 1 spawn                                                                                                   |
 * | 7                        | 2 spawns                                                                                                  |
 * | 8                        | 3 spawns                                                                                                  |
 * | Cost                     | 15,000                                                                                                    |
 * | Hits                     | 5,000                                                                                                     |
 * | Capacity                 | 300                                                                                                       |
 * | Spawn time               | 3 ticks per each body part                                                                                |
 * | Energy auto-regeneration | 1 energy unit per tick while energy available in the room (in all spawns and extensions) is less than 300 |
 */
external class StructureSpawn: OwnedStructure {

    /**
     * A shorthand to `Memory.spawns[spawn.name]`.
     * You can use it for quick access the spawn’s specific memory data object
     */
    val memory: MemoryModel

    /**
     * Spawn’s name. You choose the name upon creating a new spawn, and it cannot be changed later.
     * This name is a hash key to access the spawn via the [Game.spawns] object
     */
    val name: String

    /**
     * If the spawn is in process of spawning a new creep, this object will contain a
     * [Spawning] object, or null otherwise
     */
    val spawning: Spawning?

    /**
     * A [Store] object that contains cargo of this structure
     */
    val store: Store

    /**
     * Start the creep spawning process. The required energy amount can be withdrawn from
     * all spawns and extensions in the room
     *
     * @param body An array describing the new creep’s body. Should contain 1 to 50 elements
     *
     * @param name The name of a new creep. The name length limit is 100 characters.
     * It must be a unique creep name, i.e. the [Game.creeps] object should not contain another
     * creep with the same name (hash key)
     *
     * @param opts An object with additional options for the spawning process
     *
     * @return One of the following codes:
     *
     * [Ok] - The operation has been scheduled successfully
     *
     * [NotOwner] - You are not the owner of this spawn
     *
     * [NameExists] - There is a creep with the same name already
     *
     * [Busy] - The spawn is already in process of spawning another creep
     *
     * [NotEnoughEnergy] - The spawn and its extensions contain not enough energy to create a creep with the given body
     *
     * [InvalidArgs] - Body is not properly described or name was not provided
     *
     * [RclNotEnough] - Your Room Controller level is insufficient to use this spawn
     */
    fun spawnCreep(body: Array<BodyPart>, name: String, opts: SpawnOpts? = definedExternally): ErrorCode

    /**
     * Kill the creep and drop up to 100% of resources spent on its spawning and boosting depending
     * on remaining life time. The target should be at adjacent square. Energy return is limited
     * to 125 units per body part
     *
     * @param target The target creep object
     *
     * @return One of the following codes:
     *
     * [Ok] - The operation has been scheduled successfully
     *
     * [NotOwner] - You are not the owner of this spawn or the target creep
     *
     * [InvalidTarget] - The specified target object is not a creep
     *
     * [NotInRange] - The target creep is too far away
     *
     * [RclNotEnough] - Your Room Controller level is insufficient to use this spawn
     */
    fun recycleCreep(target: Creep): ErrorCode

    /**
     * Increase the remaining time to live of the target creep. The target should be at adjacent square.
     * The target should not have CLAIM body parts. The spawn should not be busy with the spawning process.
     * Each execution increases the creep's timer by amount of ticks according to this formula:
     * ```
     * floor(600/body_size)
     * ```
     * Energy required for each execution is determined using this formula:
     * ```
     * ceil(creep_cost/2.5/body_size)
     * ```
     * Renewing a creep removes all of its boosts
     *
     * @param target The target creep object
     *
     * @return One of the following codes:
     *
     * [Ok] - The operation has been scheduled successfully
     *
     * [NotOwner] - You are not the owner of the spawn, or the creep
     *
     * [Busy] - The spawn is spawning another creep
     *
     * [NotEnoughEnergy] - The spawn does not have enough energy
     *
     * [InvalidTarget] - The specified target object is not a creep, or the creep has CLAIM body part
     *
     * [Full] - The target creep's time to live timer is full
     *
     * [NotInRange] - The target creep is too far away
     *
     * [RclNotEnough] - Your Room Controller level is insufficient to use this spawn
     */
    fun renewCreep(target: Creep): ErrorCode
}

/**
 * Details of the creep being spawned currently that can be addressed by the [StructureSpawn.spawning] property
 */
@JsName("StructureSpawn.Spawning")
external class Spawning {

    /**
     * An array with the spawn directions, see [Spawning.setDirections]
     */
    val directions: Array<Direction>?

    /**
     * The name of a new creep
     */
    val name: String

    /**
     * Time needed in total to complete the spawning
     */
    val needTime: Number

    /**
     * Remaining time to go
     */
    val remainingTime: Number

    /**
     * A link to the spawn
     */
    val spawn: StructureSpawn

    /**
     * Cancel spawning immediately. Energy spent on spawning is not returned
     *
     * @return One of the following codes:
     *
     * [Ok] - The operation has been scheduled successfully
     *
     * [NotOwner] - You are not the owner of this spawn
     */
    fun cancel(): ErrorCode

    /**
     * Set desired directions where the creep should move when spawned
     *
     * @return One of the following codes:
     *
     * [Ok] - The operation has been scheduled successfully
     *
     * [NotOwner] - You are not the owner of this spawn
     *
     * [InvalidArgs] - The directions is array is invalid
     */
    fun setDirections(directions: Array<Direction>?): ErrorCode
}

@JsExport
class SpawnOpts(

    /**
     * Memory of the new creep. If provided, it will be immediately stored into `Memory.creeps[name]`
     */
    val memory: MemoryModel? = null,

    /**
     * Array of spawns/extensions from which to draw energy for the spawning process.
     * Structures will be used according to the array order
     */
    val energyStructures: Array<OwnedStructure>? = null,

    /**
     * If [dryRun] is true, the operation will only check if it is possible to create a creep
     */
    val dryRun: Boolean = false,

    /**
     * Set desired directions where the creep should move when spawned
     */
    val directions: Array<Int>? = null
)
