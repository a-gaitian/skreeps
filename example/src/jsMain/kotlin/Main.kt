@file:OptIn(ExperimentalJsCollectionsApi::class)

import io.github.skreeps.api.global.Game

@OptIn(ExperimentalJsExport::class)
@JsExport
fun loop() = try {



} finally {
    println("CPU used: ${Game.cpu.getUsed()}")
}