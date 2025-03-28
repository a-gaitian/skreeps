@file:OptIn(ExperimentalJsCollectionsApi::class, ExperimentalJsCollectionsApi::class, ExperimentalJsExport::class)

import io.github.skreeps.api.constants.StructureType
import io.github.skreeps.api.global.*
import io.github.skreeps.api.prototypes.CostMatrix
import io.github.skreeps.api.prototypes.RoomPosition
import io.github.skreeps.api.prototypes.RoomVisual
import io.github.skreeps.api.utils.get

@OptIn(ExperimentalJsExport::class)
@JsExport
fun loop() = try {

    val spawn1 = Game.spawns["Spawn1"]!!
    println(spawn1.structureType)
    println(spawn1.structureType == StructureType.Spawn)

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
