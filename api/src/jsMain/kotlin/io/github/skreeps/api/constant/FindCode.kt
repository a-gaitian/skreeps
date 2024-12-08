package io.github.skreeps.api.constant

import io.github.skreeps.api.Result
import io.github.skreeps.api.orThrow

interface FindResultCode: ResultCode

fun FindResultCode.asFindCode() = FindCode.valueOf(value)

enum class FindCode(val code: Int) {
    FIND_EXIT_TOP(1),
    FIND_EXIT_RIGHT(3),
    FIND_EXIT_BOTTOM(5),
    FIND_EXIT_LEFT(7),
    FIND_EXIT(10),
    FIND_CREEPS(101),
    FIND_MY_CREEPS(102),
    FIND_HOSTILE_CREEPS(103),
    FIND_SOURCES_ACTIVE(104),
    FIND_SOURCES(105),
    FIND_DROPPED_RESOURCES(106),
    FIND_STRUCTURES(107),
    FIND_MY_STRUCTURES(108),
    FIND_HOSTILE_STRUCTURES(109),
    FIND_FLAGS(110),
    FIND_CONSTRUCTION_SITES(111),
    FIND_MY_SPAWNS(112),
    FIND_HOSTILE_SPAWNS(113),
    FIND_MY_CONSTRUCTION_SITES(114),
    FIND_HOSTILE_CONSTRUCTION_SITES(115),
    FIND_MINERALS(116),
    FIND_NUKES(117),
    FIND_TOMBSTONES(118),
    FIND_POWER_CREEPS(119),
    FIND_MY_POWER_CREEPS(120),
    FIND_HOSTILE_POWER_CREEPS(121),
    FIND_DEPOSITS(122),
    FIND_RUINS(123);

    companion object {
        fun valueOf(code: Int): FindCode =
            FindCode.entries.first { it.code == code }
    }
}

fun Result<FindResultCode>.unwrap() = orThrow().asFindCode()
