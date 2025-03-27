@file:OptIn(ExperimentalJsCollectionsApi::class, ExperimentalJsCollectionsApi::class, ExperimentalJsExport::class)

import io.github.skreeps.api.global.*
import io.github.skreeps.api.utils.get

@OptIn(ExperimentalJsExport::class)
@JsExport
fun loop() = try {

    println("Counter: ${Memory.a++}, counter++")
    if (Memory.b == null) {
        println("b is null, setting to 'Hi!")
        Memory.b = "Hi!"
    } else {
        println("b is '${Memory.b}', setting to null")
        Memory.b = null
    }

    val spawn1Memory = Memory.spawns["Spawn1"]!!
    val c: Int by Game.spawns["Spawn1"]!!.memory
    println(c)

    val constantA: String? by spawn1Memory.withDefault("Hello World!")

    println("constantA: $constantA")

    var varB: Int by spawn1Memory.withDefault(0)

    println("varB: ${varB++}")

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
