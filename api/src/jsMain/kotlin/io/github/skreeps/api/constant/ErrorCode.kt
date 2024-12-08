package io.github.skreeps.api.constant

import io.github.skreeps.api.ScreepsApiException

interface ErrorResultCode : ResultCode

fun ErrorResultCode.asErrorCode() = ErrorCode.valueOf(value)

enum class ErrorCode(val code: Int) {
    OK(0),
    ERR_NOT_OWNER(-1),
    ERR_NO_PATH(-2),
    ERR_NAME_EXISTS(-3),
    ERR_BUSY(-4),
    ERR_NOT_FOUND(-5),
    ERR_NOT_ENOUGH_ENERGY(-6),
    ERR_NOT_ENOUGH_RESOURCES(-6),
    ERR_INVALID_TARGET(-7),
    ERR_FULL(-8),
    ERR_NOT_IN_RANGE(-9),
    ERR_INVALID_ARGS(-10),
    ERR_TIRED(-11),
    ERR_NO_BODYPART(-12),
    ERR_NOT_ENOUGH_EXTENSIONS(-6),
    ERR_RCL_NOT_ENOUGH(-14),
    ERR_GCL_NOT_ENOUGH(-15);

    companion object {
        fun valueOf(code: Int): ErrorCode =
            entries.first { it.code == code }
    }
}

fun ErrorResultCode.orThrow() = asErrorCode().let {
    if (it != ErrorCode.OK)
        throw ScreepsApiException(it)
}