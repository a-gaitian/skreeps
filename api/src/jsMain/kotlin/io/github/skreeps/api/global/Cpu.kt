@file:OptIn(ExperimentalJsExport::class)
@file:Suppress("NON_EXPORTABLE_TYPE")

package io.github.skreeps.api.global

import io.github.skreeps.api.constants.ErrorCode
import io.github.skreeps.api.utils.ResultMap

/**
 * An object containing information about your CPU usage
 */
external class Cpu {

    /**
     * Your assigned CPU limit for the current shard
     */
    val limit: Number

    /**
     * An amount of available CPU time at the current game tick
     *
     * Usually it is higher than [Game.cpu.limit][Cpu.limit]. [Learn more](https://docs.screeps.com/cpu-limit.html)
     */
    val tickLimit: Number

    /**
     * An amount of unused CPU accumulated in your [bucket](https://docs.screeps.com/cpu-limit.html#Bucket).
     */
    val bucket: Number

    /**
     * An object with limits for each shard with shard names as keys. You can use [setShardLimits] method to re-assign them
     */
    val shardLimits: ResultMap<Number>

    /**
     * Whether full CPU is currently unlocked for your account
     */
    val unlocked: Boolean

    /**
     * The time [in milliseconds since UNIX epoch time](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date/getTime#syntax)
     * until full CPU is unlocked
     * for your account.
     * This property is not defined when full CPU is not unlocked for your account,
     * or it's unlocked with a subscription.
     */
    val unlockedTime: Number

    /**
     * _This method is only available when **Virtual machine** is set to **Isolated** in your
     * [account runtime settings](https://screeps.com/a/#!/account/runtime)_
     *
     * Use this method to get heap statistics for your virtual machine. The return value
     * is almost identical to the Node.js function
     * [v8.getHeapStatistics()](https://nodejs.org/dist/v8.17.0/docs/api/v8.html#v8_v8_getheapstatistics).
     * This function returns one additional property: [HeapStatistics.externallyAllocatedSize]
     * which is the total amount of currently allocated memory which is not included in the v8 heap
     * but counts against this isolate's memory limit. ArrayBuffer instances over a certain size
     * are externally allocated and will be counted here
     */
    fun getHeapStatistics(): HeapStatistics

    class HeapStatistics {

        @JsName("total_heap_size")
        val totalHeapSize: Number

        @JsName("total_heap_size_executable")
        val totalHeapSizeExecutable: Number

        @JsName("total_physical_size")
        val totalPhysicalSize: Number

        @JsName("total_available_size")
        val totalAvailableSize: Number

        @JsName("used_heap_size")
        val usedHeapSize: Number

        @JsName("heap_size_limit")
        val heapSizeLimit: Number

        @JsName("malloced_memory")
        val mallocedMemory: Number

        @JsName("peak_malloced_memory")
        val peakMallocedMemory: Number

        @JsName("does_zap_garbage")
        val doesZapGarbage: Number

        @JsName("externally_allocated_size")
        val externallyAllocatedSize: Number
    }

    /**
     * Get amount of CPU time used from the beginning of the current game tick. Always returns 0
     * in the Simulation mode
     *
     * @return currently used CPU time as a float number
     */
    fun getUsed(): Number

    /**
     * _This method is only available when **Virtual machine** is set to **Isolated** in your
     * [account runtime settings](https://screeps.com/a/#!/account/runtime)_
     *
     * Reset your runtime environment and wipe all data in heap memory
     */
    fun halt()

    /**
     * Allocate CPU limits to different shards. Total amount of CPU should remain equal to
     * [Game.Cpu.shardLimits]. This method can be used only once per 12 hours
     *
     * @param limits An object with CPU values for each shard in the same format as [Game.cpu.shardLimits][Cpu.shardLimits]
     *
     * @return One of the following codes:
     *
     * [OK] - The operation has been scheduled successfully
     *
     * [ERR_BUSY] - 12-hours cooldown period is not over yet
     *
     * [ERR_INVALID_ARGS] - The argument is not a valid shard limits object
     */
    fun setShardLimits(limits: dynamic): ErrorCode

    /**
     * Unlock full CPU for your account for additional 24 hours. This method will consume
     * 1 CPU unlock bound to your account (See [Game.resources]).
     * If full CPU is not currently unlocked for your account, it may take some time
     * (up to 5 minutes) before unlock is applied to your account.
     *
     * @return One of the following codes:
     *
     * [OK] - The operation has been scheduled successfully
     *
     * [ERR_NOT_ENOUGH_RESOURCES] - Your account does not have enough _cpuUnlock_ resource
     *
     * [ERR_FULL] - Your CPU is unlocked with a subscription
     */
    fun unlock(): ErrorCode

    /**
     * Generate 1 pixel resource unit for 10000 CPU from your bucket
     *
     * @return One of the following codes:
     *
     * [OK] - The operation has been scheduled successfully
     *
     * [ERR_NOT_ENOUGH_RESOURCES] - Your bucket does not have enough CPU
     */
    fun generatePixel(): ErrorCode
}
