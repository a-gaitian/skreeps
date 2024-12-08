package io.github.skreeps.api.utils

import kotlin.enums.enumEntries

external interface Code<T: CodeEnum>

interface CodeEnum {
    val code: Int
}

val Code<*>.value
    get() = unsafeCast<Int>()

inline fun <reified T> Code<T>.asEnum(): T where T : CodeEnum, T : Enum<T> =
    value.asCodeEnum()

inline fun <reified T> Int.asCodeEnum(): T where T : CodeEnum, T : Enum<T> =
    enumEntries<T>().find { it.code == this }!!
