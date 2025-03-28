@file:OptIn(ExperimentalJsCollectionsApi::class, ExperimentalJsCollectionsApi::class, ExperimentalJsExport::class)

import io.github.skreeps.api.constants.BodyParts
import io.github.skreeps.api.constants.Directions
import io.github.skreeps.api.constants.orThrow
import io.github.skreeps.api.global.*
import io.github.skreeps.api.prototypes.SpawnOpts
import io.github.skreeps.api.utils.get

@OptIn(ExperimentalJsExport::class)
@JsExport
fun loop() = try {

    val spawn1 = Game.spawns["Spawn1"]!!
    if (spawn1.spawning != null) {
        println("Creep spawning ${spawn1.spawning}")
    } else {
        println("Spawning new creep")
        spawn1.spawnCreep(
            arrayOf(BodyParts.Move),
            "Worker_${Game.time}",
            SpawnOpts(directions = arrayOf(Directions.LEFT))
        ).orThrow()
    }

} catch (e: Throwable) {
    println(e.stackTraceToString())
} finally {
    println("--- --- --- --- Tick ${Game.time} | CPU ${Game.cpu.getUsed().asDynamic().toFixed(2)} --- --- --- ---")
}

val Memory: ExampleMemory
    get() = GlobalMemory.getAs<ExampleMemory>()

@JsExport
data class ExampleMemory(
    var a: Int,
    var b: String?,

): WithCreepModel<MemoryModel>, WithSpawnModel<SpawnMemory>

@JsExport
data class SpawnMemory(
    var c: Int
): MemoryModel
