package io.github.skreeps.api.utils

import io.github.skreeps.api.constants.Error

external class Result<T>

fun <T> Result<T>.orThrow(): T {
    if (jsTypeOf(this) == "number") {
        try {
            this.unsafeCast<Code<Error>>().asEnum().orThrow()
        } catch (e: NoSuchElementException) {
            // number, but not error
        }
    }
    return this.unsafeCast<T>()
}
