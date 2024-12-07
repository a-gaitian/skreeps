import io.github.skreeps.raw.api.Game

@OptIn(ExperimentalJsExport::class)
@JsName("loop")
@JsExport
fun loop() {
    console.log(Game.gcl)
}