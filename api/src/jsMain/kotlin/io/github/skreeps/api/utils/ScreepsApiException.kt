package io.github.skreeps.api.utils

import io.github.skreeps.api.constants.Error

class ScreepsApiException(
    val code: Error,
) : RuntimeException(code.name)
