@file:OptIn(ExperimentalJsExport::class)
@file:Suppress("NON_EXPORTABLE_TYPE")

package io.github.skreeps.api.prototypes

import io.github.skreeps.api.constants.*
import io.github.skreeps.api.global.*

/**
 * Creeps are your units. Creeps can move, harvest energy, construct structures, attack another
 * creeps, and perform other actions. Each creep consists of up to 50 [BodyParts]
 */
external class Creep : RoomObject, CreepTarget {

    /**
     * An array describing the creep’s body
     */
    val body: Array<CreepBodyPart>

    /**
     * The movement fatigue indicator. If it is greater than zero, the creep cannot move
     */
    val fatigue: Number

    /**
     * The current amount of hit points of the creep
     */
    val hits: Number

    /**
     * The maximum amount of hit points of the creep
     */
    val hitsMax: Number

    /**
     * A unique object identificator. You can use [Game.getObjectById] method
     * to retrieve an object instance by its [id]
     */
    val id: String

    /**
     * A shorthand to `Memory.creeps[creep.name]`. You can use it for quick access the creep’s specific memory data object
     */
    val memory: MemoryModel

    /**
     * Whether it is your creep or foe
     */
    val my: Boolean

    /**
     * Creep’s name. You can choose the name while creating a new creep, and it cannot be changed later.
     * This name is a hash key to access the creep via the [Game.creeps] object
     */
    val name: String

    /**
     * An object with the creep’s owner info
     */
    val owner: Owner

    /**
     * The text message that the creep was saying at the last tick
     */
    val saying: String

    /**
     * Whether this creep is still being spawned
     */
    val spawning: Boolean

    /**
     * A [Store] object that contains cargo of this creep
     */
    val store: Store

    /**
     * The remaining amount of game ticks after which the creep will die
     */
    val ticksToLive: Number

    /**
     * Attack another creep, power creep, or structure in a short-ranged attack. Requires the
     * [BodyParts.Attack]. If the target is inside a rampart, then the rampart is attacked instead.
     * The target has to be at adjacent square to the creep. If the target is a creep with
     * [BodyParts.Attack] body parts and is not inside a rampart, it will automatically hit back at the attacker
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid attackable object
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Attack] in this creep’s body
     */
    fun attack(target: AttackTarget): ErrorCode

    /**
     * Decreases the controller's downgrade timer by 300 ticks per every [BodyParts.Claim] body part,
     * or reservation timer by 1 tick per every [BodyParts.Claim] body part. If the controller under attack
     * is owned, it cannot be upgraded or attacked again for the next 1,000 ticks.
     * The target has to be at adjacent square to the creep
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid owned or reserved controller object
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_TIRED][ErrorCodes.Tired] - You have to wait until the next attack is possible
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are not enough [BodyParts.Claim] in this creep’s body
     *
     */
    fun attackController(controller: StructureController): ErrorCode

    /**
     * Build a structure at the target construction site using carried energy.
     * Requires [BodyParts.Work] and [BodyParts.Carry] body parts.
     * The target has to be within 3 squares range of the creep
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_ENOUGH_RESOURCES][ErrorCodes.NotEnoughResources] - The creep does not have any carried energy
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid construction site object or the structure cannot be built here (probably because of a creep at the same square)
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Work] in this creep’s body
     *
     */
    fun build(target: ConstructionSite): ErrorCode

    /**
     * Cancel the order given during the current game tick
     *
     * @param methodName The name of a creep's method to be cancelled
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been cancelled successfully
     *
     * [ERR_NOT_FOUND][ErrorCodes.NotFound] - The order with the specified name is not found
     *
     */
    fun cancelOrder(methodName: String): ErrorCode

    /**
     * Claims a neutral controller under your control. Requires the [BodyParts.Claim] body part.
     * The target has to be at adjacent square to the creep. You need to have the corresponding
     * Global Control Level in order to claim a new room.
     * If you don't have enough GCL, consider [reserving](https://docs.screeps.com/api/#reserveController)
     * this room instead. [Learn more](https://docs.screeps.com/control.html#Global-Control-Level)
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid neutral controller object
     *
     * [ERR_FULL][ErrorCodes.Full] - You cannot claim more than 3 rooms in the Novice Area
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Claim] in this creep’s body
     *
     * [ERR_GCL_NOT_ENOUGH][ErrorCodes.GclNotEnough] - Your Global Control Level is not enough
     *
     */
    fun claimController(controller: StructureController): ErrorCode

    /**
     * Dismantles any structure that can be constructed (even hostile) returning 50% of
     * the energy spent on its repair. Requires the [BodyParts.Work] body part.
     * If the creep has an empty [BodyParts.Carry] body part, the energy is put into it;
     * otherwise it is dropped on the ground. The target has to be at adjacent square to the creep
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid structure object
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Work] in this creep’s body
     *
     */
    fun dismantle(target: Structure): ErrorCode

    /**
     * Drop this resource on the ground
     *
     * @param resourceType One of the [Resources] constants
     * @param amount The amount of resource units to be dropped. If omitted, all the available carried amount is used
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_ENOUGH_RESOURCES][ErrorCodes.NotEnoughResources] - The creep does not have the given amount of resources
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The resourceType is not a valid [Resources] constant
     *
     */
    fun drop(resourceType: Resource, amount: Number? = definedExternally): ErrorCode

    /**
     * Add one more available safe mode activation to a room controller.
     * The creep has to be at adjacent square to the target room controller and have 1000 ghodium resource
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_ENOUGH_RESOURCES][ErrorCodes.NotEnoughResources] - The creep does not have enough [Resources.Ghodium]
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid controller object
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     */
    fun generateSafeMode(target: StructureController): ErrorCode

    /**
     * Get the quantity of live body parts of the given type. Fully damaged parts do not count
     *
     * @return A number representing the quantity of body parts
     */
    fun getActiveBodyparts(type: BodyPart): Number

    /**
     * Harvest energy from the source or resources from minerals and deposits.
     * Requires the [BodyParts.Work] body part. If the creep has an empty [BodyParts.Carry] body part,
     * the harvested resource is put into it; otherwise it is dropped on the ground.
     * The target has to be at an adjacent square to the creep
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep, or the room controller is owned or reserved by another player
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_FOUND][ErrorCodes.NotFound] - Extractor not found. You must build an extractor structure to harvest minerals
     *
     * [ERR_NOT_ENOUGH_RESOURCES][ErrorCodes.NotEnoughResources] - The target does not contain any harvestable energy or mineral
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid source or mineral object
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_TIRED][ErrorCodes.Tired] - The extractor or the deposit is still cooling down
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Work] in this creep’s body
     *
     */
    fun harvest(target: HarvestTarget): ErrorCode

    /**
     * Heal self or another creep. It will restore the target creep’s damaged body parts function
     * and increase the hits counter. Requires the [BodyParts.Heal] body part.
     * The target has to be at adjacent square to the creep
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid creep object
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Heal] in this creep’s body
     *
     */
    fun heal(target: CreepTarget): ErrorCode

    /**
     * Move the creep one square in the specified direction. Requires the [BodyParts.Move] body part,
     * or another creep nearby pulling the creep. In case if you call [move] on a creep nearby,
     * the [ERR_TIRED][ErrorCodes.Tired] and the [ERR_NO_BODYPART][ErrorCodes.NoBodypart] checks will be bypassed; otherwise,
     * the [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] check will be bypassed
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target creep is too far away
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The provided direction is incorrect
     *
     * [ERR_TIRED][ErrorCodes.Tired] - The fatigue indicator of the creep is non-zero
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Move] in this creep’s body
     *
     */
    fun move(direction: Direction): ErrorCode

    /**
     * Move the creep one square in the specified direction. Requires the [BodyParts.Move] body part,
     * or another creep nearby pulling the creep. In case if you call [move] on a creep nearby,
     * the [ERR_TIRED][ErrorCodes.Tired] and the [ERR_NO_BODYPART][ErrorCodes.NoBodypart] checks will be bypassed; otherwise,
     * the [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] check will be bypassed
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target creep is too far away
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The provided direction is incorrect
     *
     * [ERR_TIRED][ErrorCodes.Tired] - The fatigue indicator of the creep is non-zero
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Move] in this creep’s body
     *
     */
    fun move(creep: Creep): ErrorCode

    /**
     * Move the creep using the specified predefined path. Requires the [BodyParts.Move] body part
     *
     * @param path A path value as returned from [Room.findPath], [RoomPosition.findPathTo],
     * or [PathFinder.search] methods. Both array form and serialized string form are accepted
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_FOUND][ErrorCodes.NotFound] - The specified path doesn't match the creep's location
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - path is not a valid path array
     *
     * [ERR_TIRED][ErrorCodes.Tired] - The fatigue indicator of the creep is non-zero
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Move] in this creep’s body
     *
     */
    fun moveByPath(path: Array<RoomPosition>): ErrorCode

    /**
     * Move the creep using the specified predefined path. Requires the [BodyParts.Move] body part
     *
     * @param path A path value as returned from [Room.findPath], [RoomPosition.findPathTo],
     * or [PathFinder.search] methods. Both array form and serialized string form are accepted
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_FOUND][ErrorCodes.NotFound] - The specified path doesn't match the creep's location
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - path is not a valid path array
     *
     * [ERR_TIRED][ErrorCodes.Tired] - The fatigue indicator of the creep is non-zero
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Move] in this creep’s body
     *
     */
    fun moveByPath(path: String): ErrorCode

    /**
     * Find the optimal path to the target within the same room and move to it.
     * A shorthand to consequent calls of `pos.findPathTo()` and `move()` methods.
     * If the target is in another room, then the corresponding exit will be used as a target.
     * Requires the [BodyParts.Move] body part
     *
     * @param x X position of the target in the same room
     * @param y Y position of the target in the same room
     * @param target Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject]).
     * The position doesn't have to be in the same room with the creep
     * @param opts An object containing additional options
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_NO_PATH][ErrorCodes.NoPath] - No path to the target could be found
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_FOUND][ErrorCodes.NotFound] - The creep has no memorized path to reuse
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target provided is invalid
     *
     * [ERR_TIRED][ErrorCodes.Tired] - The fatigue indicator of the creep is non-zero
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Move] in this creep’s body
     *
     */
    fun moveTo(x: Number, y: Number, opts: MoveToOpts = definedExternally): ErrorCode

    /**
     * Find the optimal path to the target within the same room and move to it.
     * A shorthand to consequent calls of `pos.findPathTo()` and `move()` methods.
     * If the target is in another room, then the corresponding exit will be used as a target.
     * Requires the [BodyParts.Move] body part
     *
     * @param x X position of the target in the same room
     * @param y Y position of the target in the same room
     * @param target Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject]).
     * The position doesn't have to be in the same room with the creep
     * @param opts An object containing additional options
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_NO_PATH][ErrorCodes.NoPath] - No path to the target could be found
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_FOUND][ErrorCodes.NotFound] - The creep has no memorized path to reuse
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target provided is invalid
     *
     * [ERR_TIRED][ErrorCodes.Tired] - The fatigue indicator of the creep is non-zero
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Move] in this creep’s body
     *
     */
    fun moveTo(target: RoomPosition, opts: MoveToOpts = definedExternally): ErrorCode

    /**
     * Find the optimal path to the target within the same room and move to it.
     * A shorthand to consequent calls of `pos.findPathTo()` and `move()` methods.
     * If the target is in another room, then the corresponding exit will be used as a target.
     * Requires the [BodyParts.Move] body part
     *
     * @param x X position of the target in the same room
     * @param y Y position of the target in the same room
     * @param target Can be a [RoomPosition] object or any object containing [RoomPosition] ([RoomObject]).
     * The position doesn't have to be in the same room with the creep
     * @param opts An object containing additional options
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_NO_PATH][ErrorCodes.NoPath] - No path to the target could be found
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_FOUND][ErrorCodes.NotFound] - The creep has no memorized path to reuse
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target provided is invalid
     *
     * [ERR_TIRED][ErrorCodes.Tired] - The fatigue indicator of the creep is non-zero
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Move] in this creep’s body
     *
     */
    fun moveTo(target: RoomObject, opts: MoveToOpts = definedExternally): ErrorCode

    /**
     * Toggle auto notification when the creep is under attack. The notification will be sent to your account email. Turned on by default
     *
     * @param enabled Whether to enable notification or disable
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - enable argument is not a boolean value
     *
     */
    fun notifyWhenAttacked(enabled: Boolean): ErrorCode

    /**
     * Pick up an item (a dropped piece of energy). Requires the [BodyParts.Carry] body part.
     * The target has to be at adjacent square to the creep or at the same square
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid object to pick up
     *
     * [ERR_FULL][ErrorCodes.Full] - The creep cannot receive any more resource
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     */
    fun pickup(target: Resource): ErrorCode

    /**
     * Help another creep to follow this creep. The fatigue generated for the target's move will
     * be added to the creep instead of the target. Requires the [BodyParts.Move] body part.
     * The target has to be at adjacent square to the creep. The creep must [move] elsewhere,
     * and the target must [move] towards the creep
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target provided is invalid
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     */
    fun pull(target: Creep): ErrorCode

    /**
     * A ranged attack against another creep or structure. Requires the [BodyParts.RangedAttack] body part.
     * If the target is inside a rampart, the rampart is attacked instead.
     * The target has to be within 3 squares range of the creep
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid attackable object
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.RangedAttack] in this creep’s body
     *
     */
    fun rangedAttack(target: AttackTarget): ErrorCode

    /**
     * Heal another creep at a distance. It will restore the target creep’s damaged body parts
     * function and increase the hits counter. Requires the [BodyParts.Heal] body part.
     * The target has to be within 3 squares range of the creep
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid creep object
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Heal] in this creep’s body
     *
     */
    fun rangedHeal(target: Creep): ErrorCode


    /**
     * A ranged attack against all hostile creeps or structures within 3 squares range.
     * Requires the [BodyParts.RangedAttack] body part. The attack power depends on the range to each target.
     * Friendly units are not affected
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.RangedAttack] in this creep’s body
     *
     */
    fun rangedMassAttack(): ErrorCode

    /**
     * Repair a damaged structure using carried energy.
     * Requires the [BodyParts.Work] and [BodyParts.Carry] body parts.
     * The target has to be within 3 squares range of the creep
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_ENOUGH_RESOURCES][ErrorCodes.NotEnoughResources] - The creep does not carry any energy
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid structure object
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Work] in this creep’s body
     *
     */
    fun repair(target: Structure): ErrorCode

    /**
     * Temporarily block a neutral controller from claiming by other players and restore energy
     * sources to their full capacity. Each tick, this command increases the counter of
     * the period during which the controller is unavailable by 1 tick per each [BodyParts.Claim] body part.
     * The maximum reservation period to maintain is 5,000 ticks.
     * The target has to be at adjacent square to the creep
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid neutral controller object
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Claim] in this creep’s body
     *
     */
    fun reserveController(controller: StructureController): ErrorCode

    /**
     * Display a visual speech balloon above the creep with the specified message.
     * The message will be available for one tick. You can read the last message using
     * the [saying] property. Any valid Unicode characters are allowed, including
     * [emoji](http://unicode.org/emoji/charts/emoji-style.txt)
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     */
    fun say(message: String, public: Boolean = definedExternally): ErrorCode

    /**
     * Sign a controller with an arbitrary text visible to all players. This text will appear
     * in the room UI, in the world map, and can be accessed via the API. You can sign unowned
     * and hostile controllers. The target has to be at adjacent square to the creep.
     * Pass an empty string to remove the sign
     *
     * @param text The sign text. The string is cut off after 100 characters
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid controller object
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     */
    fun signController(target: StructureController, text: String): ErrorCode

    /**
     * Kill the creep immediately
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     */
    fun suicide(): ErrorCode

    /**
     * Transfer resource from the creep to another object. The target has to be at adjacent square to the creep
     *
     * @param amount The amount of resources to be transferred. If omitted, all the available carried amount is used
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_ENOUGH_RESOURCES][ErrorCodes.NotEnoughResources] - The creep does not have the given amount of resources
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid object which can contain the specified resource
     *
     * [ERR_FULL][ErrorCodes.Full] - The target cannot receive any more resources
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The resourceType is not one of the [Resources], or the amount is incorrect
     *
     */
    fun transfer(target: TransferTarget, resourceType: Resource, amount: Number? = definedExternally): ErrorCode

    /**
     * Upgrade your controller to the next level using carried energy. Upgrading controllers raises
     * your Global Control Level in parallel. Requires [BodyParts.Work] and [BodyParts.Carry] body parts.
     * The target has to be within 3 squares range of the creep.
     *
     * A fully upgraded level 8 controller can't be upgraded over 15 energy units per tick
     * regardless of creeps abilities. The cumulative effect of all the creeps performing
     * [upgradeController] in the current tick is taken into account. This limit can be increased
     * by using [ghodium mineral boost](https://docs.screeps.com/resources.html).
     *
     * Upgrading the controller raises its [StructureController.ticksToDowngrade] timer by 100. The timer must be full
     * in order for controller to be levelled up
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep or the target controller
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_ENOUGH_RESOURCES][ErrorCodes.NotEnoughResources] - The creep does not have any carried energy
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid controller object, or the controller upgrading is blocked
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_NO_BODYPART][ErrorCodes.NoBodypart] - There are no [BodyParts.Work] in this creep’s body
     *
     */
    fun upgradeController(controller: StructureController): ErrorCode

    /**
     * Withdraw resources from a structure or tombstone. The target has to be at adjacent square
     * to the creep. Multiple creeps can withdraw from the same object in the same tick.
     * Your creeps can withdraw resources from hostile structures/tombstones as well,
     * in case if there is no hostile rampart on top of it.
     *
     * This method should not be used to transfer resources between creeps.
     * To transfer between creeps, use the [transfer] method on the original creep
     *
     * @param amount The amount of resources to be transferred. If omitted, all the available amount is used
     *
     * @return One of the following codes:
     *
     * [OK][ErrorCodes.Ok] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER][ErrorCodes.NotOwner] - You are not the owner of this creep, or there is a hostile rampart on top of the target
     *
     * [ERR_BUSY][ErrorCodes.Busy] - The creep is still being spawned
     *
     * [ERR_NOT_ENOUGH_RESOURCES][ErrorCodes.NotEnoughResources] - The target does not have the given amount of resources
     *
     * [ERR_INVALID_TARGET][ErrorCodes.InvalidTarget] - The target is not a valid object which can contain the specified resource
     *
     * [ERR_FULL][ErrorCodes.Full] - The creep's carry is full
     *
     * [ERR_NOT_IN_RANGE][ErrorCodes.NotInRange] - The target is too far away
     *
     * [ERR_INVALID_ARGS][ErrorCodes.InvalidArgs] - The resourceType is not one of the [Resources], or the amount is incorrect
     *
     */
    fun withdraw(target: WithdrawTarget, resourceType: Resource, amount: Number? = definedExternally): ErrorCode
}

external class CreepBodyPart {

    /**
     * If the body part is boosted, this property specifies the mineral type which is used for boosting.
     * One of the [Resources] constants
     */
    val boost: String?

    /**
     * One of the [BodyParts] constants
     */
    val type: BodyPart

    /**
     * The remaining amount of hit points of this body part
     */
    val hits: Number
}

@JsExport
class MoveToOpts(

    /**
     * This option enables reusing the path found along multiple game ticks.
     * It allows to save CPU time, but can result in a slightly slower creep reaction behavior.
     * The path is stored into the creep's memory to the `_move` property.
     * The [reusePath] value defines the amount of ticks which the path should be reused for.
     * Increase the amount to save more CPU, decrease to make the movement more consistent.
     * Set to 0 if you want to disable
     */
    val reusePath: Number = 5,

    /**
     * If reusePath is enabled and this option is set to true, the path will be stored in memory
     * in the short serialized form using [Room.serializePath]
     */
    val serializeMemory: Boolean = true,

    /**
     * If this option is set to true, [moveTo][Creep.moveTo] method will return [ERR_NOT_FOUND][ErrorCodes.NotFound] if there
     * is no memorized path to reuse. This can significantly save CPU time in some cases
     */
    val noPathFinding: Boolean = false,

    /**
     * Draw a line along the creep’s path using [RoomVisual.poly]
     */
    val visualizePathStyle: ShapeStyle = ShapeStyle(
        fillColor = Color.Transparent,
        lineStyle = LineStyle(
            color = Color.White,
            lineVariant = LineVariant.Dashed,
            width = 0.15,
            opacity = 0.1
        )
    ),

    /**
     * Any options supported by [Room.findPath] method
     */
    private val findPathOpts: RoomFindPathOpts = RoomFindPathOpts()

) : FindPathOpts by findPathOpts
