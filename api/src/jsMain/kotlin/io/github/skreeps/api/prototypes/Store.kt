package io.github.skreeps.api.prototypes

import io.github.skreeps.api.constants.Resource

/**
 * An object that can contain resources in its cargo.
 *
 * There are two types of stores in the game: general purpose stores and limited stores.
 *
 * - General purpose stores can contain any resource within its capacity
 * (e.g. creeps, containers, storages, terminals).
 *
 * - Limited stores can contain only a few types of resources needed for that particular object
 * (e.g. spawns, extensions, labs, nukers).
 *
 * The [Store] prototype is the same for both types of stores, but they have different behavior
 * depending on the `resource` argument in its methods.
 *
 * You can get specific resources from the store by addressing them as object properties:
 *
 * ```
 * creep.store[Resource.Energy]
 * ```
 */
external class Store {

    /**
     * Returns capacity of this store for the specified resource. For a general purpose store,
     * it returns total capacity if [resource] is undefined
     *
     * @param resource The type of the resource
     *
     * @return capacity number, or `null` in case of an invalid [resource] for this store type
     */
    fun getCapacity(resource: Resource? = definedExternally): Number?

    /**
     * Returns capacity of this store for the specified resource
     *
     * @return capacity number
     */
    fun getCapacity(): Number

    /**
     * Returns free capacity for the store. For a limited store, it returns the capacity available
     * for the specified [resource] if resource is defined and valid for this store
     *
     * @param resource The type of the resource
     *
     * @return available capacity number, or `null` in case of an invalid [resource] for this store type
     */
    fun getFreeCapacity(resource: Resource? = definedExternally): Number?

    /**
     * Returns free capacity for the store
     *
     * @return available capacity number
     */
    fun getFreeCapacity(): Number

    /**
     * Returns the capacity used by the specified resource. For a general purpose store,
     * it returns total used capacity if [resource] is undefined
     *
     * @param resource The type of the resource
     *
     * @return used capacity number, or `null` in case of an invalid [resource] for this store type
     */
    fun getUsedCapacity(resource: Resource? = definedExternally): Number?

    /**
     * Returns the capacity used by the specified resource
     * @return used capacity number
     */
    fun getUsedCapacity(): Number

    operator fun get(resource: Resource?): Number?
}