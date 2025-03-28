package io.github.skreeps.api.utils

import kotlin.js.Date

/**
 * [Time in milliseconds since UNIX epoch time](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date/getTime#Syntax)
 */
typealias Timestamp = Number

fun Timestamp.asDate() = Date(this)
