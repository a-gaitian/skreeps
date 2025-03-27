package io.github.skreeps.api.utils

import kotlin.js.Date

/**
 * [Time in milliseconds since UNIX epoch time](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date/getTime#Syntax)
 */
external interface Timestamp

val Timestamp.value: Long
    get() = unsafeCast<Long>()

fun Timestamp.asDate() = Date(value)
