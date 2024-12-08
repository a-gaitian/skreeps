package io.github.skreeps.api.utils

import kotlin.js.Date

external interface Timestamp

val Timestamp.value: Long
    get() = unsafeCast<Long>()

fun Timestamp.asDate() = Date(value)
