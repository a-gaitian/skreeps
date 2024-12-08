package io.github.skreeps.api

import io.github.skreeps.api.constant.ErrorCode

class ScreepsApiException(
    val code: ErrorCode,
) : RuntimeException(code.name)
