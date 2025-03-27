package io.github.skreeps.api.utils

external interface ResultMap<T>

fun <T> ResultMap<T>.asMap(): Map<String, T> =
    entries().toMap()

fun <T> ResultMap<T>.entries(): List<Pair<String, T>> =
    Object.entries(this).map { entry ->
        entry[0].unsafeCast<String>() to entry[1].unsafeCast<T>()
    }

fun <T> ResultMap<T>.keys(): List<String> =
    Object.keys(this).map { it.unsafeCast<String>() }

fun <T> ResultMap<T>.values(): List<T> =
    Object.values(this).map { it.unsafeCast<T>() }

operator fun <T> ResultMap<T>.get(key: String): T? =
    asDynamic()[key].unsafeCast<T>()

operator fun <T> ResultMap<T>.set(field: String, value: T) {
    asDynamic()[field] = value
}
