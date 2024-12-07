import io.github.skreeps.raw.api.Game
import kotlin.random.Random

@OptIn(ExperimentalJsExport::class)
@JsExport
fun loop() {
    console.log(JSON.stringify(Game.gcl))
    console.log(Random.nextInt())
}