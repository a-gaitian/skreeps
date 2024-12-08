package io.github.skreeps.api.utils

import kotlin.enums.enumEntries

external interface StringEnum<T>

val StringEnum<*>.value
    get() = unsafeCast<String>()

inline fun <reified T: Enum<T>> StringEnum<T>.asEnum(): T =
    enumEntries<T>().find { it.name.equals(value, true) }!!