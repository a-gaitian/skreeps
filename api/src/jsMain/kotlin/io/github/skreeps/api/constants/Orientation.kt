package io.github.skreeps.api.constants

import io.github.skreeps.api.utils.CodeEnum

enum class Orientation(override val code: Int) : CodeEnum {
    TOP(1),
    TOP_RIGHT(2),
    RIGHT(3),
    BOTTOM_RIGHT(4),
    BOTTOM(5),
    BOTTOM_LEFT(6),
    LEFT(7),
    TOP_LEFT(8);
}
