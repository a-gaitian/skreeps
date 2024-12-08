@file:OptIn(ExperimentalJsCollectionsApi::class)

import io.github.skreeps.api.global.Game
import io.github.skreeps.api.utils.orThrow
import kotlin.random.Random

object SomeConstants {
    var x: Int? = null
}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun loop() = try {
    println(
        Game.map.findRoute("W36S21", "W36S21").orThrow().toList()
    )
    if (SomeConstants.x == null) {
        SomeConstants.x = Random.nextInt()
        println("x is null, putting ${SomeConstants.x}")
    } else {
        println("x containing ${SomeConstants.x}")
    }
} finally {
    println("CPU used: ${Game.cpu.getUsed()}")
}