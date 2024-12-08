package io.github.skreeps.api.utils

external object Object {
    fun entries(obj: Any): Array<Array<Any>>
    fun keys(obj: Any): Array<String>
    fun values(obj: Any): Array<Any>
}
