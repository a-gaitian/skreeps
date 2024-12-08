package io.github.skreeps.api

import io.github.skreeps.api.constant.ErrorResultCode
import io.github.skreeps.api.constant.orThrow

external class Result<T>

fun <T> Result<T>.orThrow(): T {
    if (jsTypeOf(this) == "number") {
        try {
            this.unsafeCast<ErrorResultCode>().orThrow()
        } catch (e: NoSuchElementException) {
            // number, but not error
        }
    }
    return this.unsafeCast<T>()
}
