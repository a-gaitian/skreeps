@file:OptIn(ExperimentalJsCollectionsApi::class, ExperimentalJsCollectionsApi::class)

import io.github.skreeps.api.global.Game

@OptIn(ExperimentalJsExport::class)
@JsExport
fun loop() = try {

    println(Game.cpu.shardLimits)

} finally {
    println("CPU used: ${Game.cpu.getUsed()}")
}