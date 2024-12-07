package io.github.skreeps.raw.api

external object Game {
    val gcl: GlobalLevel

    class GlobalLevel {
        val level: Int
        val progress: Int
        val progressTotal: Int
    }
}
