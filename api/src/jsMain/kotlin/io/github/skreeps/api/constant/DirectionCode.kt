package io.github.skreeps.api.constant

import io.github.skreeps.api.Result
import io.github.skreeps.api.orThrow

interface DirectionResultCode : ResultCode

fun DirectionResultCode.asDirectionCode() = DirectionCode.valueOf(value)

enum class DirectionCode(val code: Int) {
    TOP(1),
    TOP_RIGHT(2),
    RIGHT(3),
    BOTTOM_RIGHT(4),
    BOTTOM(5),
    BOTTOM_LEFT(6),
    LEFT(7),
    TOP_LEFT(8);

    companion object {
        fun valueOf(code: Int): DirectionCode =
            DirectionCode.entries.first { it.code == code }
    }
}

fun Result<DirectionResultCode>.unwrap() = orThrow().asDirectionCode()
