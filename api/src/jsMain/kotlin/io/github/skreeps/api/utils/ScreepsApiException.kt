package io.github.skreeps.api.utils

import io.github.skreeps.api.constants.ErrorCode
import io.github.skreeps.api.constants.message

class ScreepsApiException(
    private val code: ErrorCode,
) : RuntimeException(code.message)
