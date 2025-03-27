package io.github.skreeps.api.global

import io.github.skreeps.api.utils.ResultMap
import io.github.skreeps.api.utils.asMap
import kotlin.reflect.KProperty

@JsName("Memory")
private external var memory: dynamic = definedExternally

@JsName("RawMemory")
private external var rawMemory: dynamic = definedExternally

object GlobalMemory {
    fun <T> getAs() =
        memory.unsafeCast<T>()
}


external interface MemoryModel


operator fun <T> MemoryModel.getValue(thisRef: Any?, property: KProperty<*>): T {
    return asDynamic()[property.name].unsafeCast<T>()
}

operator fun MemoryModel.setValue(thisRef: Any?, property: KProperty<*>, value: Any?) {
    asDynamic()[property.name] = value
}


fun <T> MemoryModel.withDefault(default: T) = WithDefaultDelegate(this, default)

class WithDefaultDelegate<T>(
    private val memory: MemoryModel,
    private val default: T
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return memory.getValue<T>(thisRef, property).let {
            if (it == null) {
                setValue(thisRef, property, default)
                default
            } else it
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Any?) {
        memory.setValue(thisRef, property, value)
    }
}

private fun <T> MemoryModel.getOrCreate(field: String): ResultMap<T> {
    if (asDynamic()[field] == null) {
        asDynamic()[field] = js("{}")
    }
    return asDynamic()[field].unsafeCast<ResultMap<T>>()
}

external interface WithCreepModel<T>: MemoryModel
val <T> WithCreepModel<T>.creeps: Map<String, T>
    get() = getOrCreate<T>("creeps").asMap()

external interface WithSpawnModel<T>: MemoryModel
val <T> WithSpawnModel<T>.spawns: Map<String, T>
    get() = getOrCreate<T>("spawns").asMap()

external interface WithRoomModel<T>: MemoryModel
val <T> WithRoomModel<T>.rooms: Map<String, T>
    get() = getOrCreate<T>("rooms").asMap()

external interface WithFlagModel<T>: MemoryModel
val <T> WithFlagModel<T>.flags: Map<String, T>
    get() = getOrCreate<T>("flags").asMap()
