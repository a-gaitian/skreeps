@file:OptIn(ExperimentalJsCollectionsApi::class, ExperimentalJsCollectionsApi::class, ExperimentalJsExport::class)

import io.github.skreeps.api.global.*
import io.github.skreeps.api.prototypes.CostMatrix
import io.github.skreeps.api.prototypes.RoomPosition
import io.github.skreeps.api.prototypes.RoomVisual

@OptIn(ExperimentalJsExport::class)
@JsExport
fun loop() = try {

    val result = PathFinder.search(
        RoomPosition(29, 27, "W33S27"),
        PathFinderGoal(RoomPosition(24, 13, "W33S27"), 1),
        PathFinderOptions {
            CostMatrix().apply {
                set(30, 26, 5)
            }
        }
    )

    println("ops: ${result.ops}, cost: ${result.cost}, incomplete: ${result.incomplete}")
    result.path.forEach {
        print("(${it.x}, ${it.y}) -> ")
    }
    println("Target")

    RoomVisual("W33S27")
        .poly(result.path, ShapeStyle(fillColor = Color.Transparent, lineStyle = LineStyle(lineVariant = LineVariant.Dashed)))

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
