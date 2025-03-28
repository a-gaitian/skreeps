package io.github.skreeps.api.constants

import io.github.skreeps.api.utils.ScreepsApiException

typealias ErrorCode = Int

object ErrorCodes {
    const val Ok = 0
    const val NotOwner = -1
    const val NoPath = -2
    const val NameExists = -3
    const val Busy = -4
    const val NotFound = -5
    const val NotEnoughEnergy = -6
    const val NotEnoughResources = -6
    const val NotEnoughExtensions = -6
    const val InvalidTarget = -7
    const val Full = -8
    const val NotInRange = -9
    const val InvalidArgs = -10
    const val Tired = -11
    const val NoBodypart = -12
    const val RclNotEnough = -14
    const val GclNotEnough = -15
}

fun ErrorCode.orThrow() = if (isError) throw ScreepsApiException(this) else Unit

val ErrorCode.isError: Boolean
    get() = this < 0

val ErrorCode.message: String
    get() = when (this) {
        0 -> "OK: Operation scheduled successfully"
        -1 -> "ERR_NOT_OWNER: You are not the owner of this object"
        -2 -> "ERR_NO_PATH: Path can not be found"
        -3 -> "ERR_NAME_EXISTS: There is a object with the same name already"
        -4 -> "ERR_BUSY"
        -5 -> "ERR_NOT_FOUND"
        -6 -> "ERR_NOT_ENOUGH_ENERGY, _RESOURCES or _EXTENSIONS"
        -7 -> "ERR_INVALID_TARGET"
        -8 -> "ERR_FULL"
        -9 -> "ERR_NOT_IN_RANGE"
        -10 -> "ERR_INVALID_ARGS"
        -11 -> "ERR_TIRED"
        -12 -> "ERR_NO_BODYPART"
        -14 -> "ERR_RCL_NOT_ENOUGH"
        -15 -> "ERR_GCL_NOT_ENOUGH"
        else -> "Unknown error code '$this'"
    }

