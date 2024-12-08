@file:OptIn(ExperimentalJsCollectionsApi::class)

import io.github.skreeps.api.global.Game
import io.github.skreeps.api.global.routeOpts
import io.github.skreeps.api.orThrow
import kotlin.js.collections.toList

@OptIn(ExperimentalJsExport::class)
@JsExport
fun loop() = try {
    val routeOpts = routeOpts { roomName, fromRoomName ->
        println("$fromRoomName -> $roomName")
        if (roomName == "E44N23")
            js("Infinity").unsafeCast<Number>()
        else
            1
    }
    println(Game.map.findRoute("E43N25", "E45N23", routeOpts).orThrow().toList())
} finally {
    println("CPU used: ${Game.cpu.getUsed()}")
}