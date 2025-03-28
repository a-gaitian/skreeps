package io.github.skreeps.api.utils

import io.github.skreeps.api.constants.ErrorCode

external class Result<T>

fun <T> Result<T>.orThrow(): T {
    if (jsTypeOf(this) == "number") {
        this.unsafeCast<ErrorCode>().let {
            if (it < 0)
                throw ScreepsApiException(it)
        }
    }
    return this.unsafeCast<T>()
}
