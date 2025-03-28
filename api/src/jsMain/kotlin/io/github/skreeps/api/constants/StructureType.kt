package io.github.skreeps.api.constants

interface StructureType {
    companion object {
        private fun of(value: String) =
            value.unsafeCast<StructureType>()

        val Spawn = of("spawn")
        val Extension = of("extension")
        val Road = of("road")
        val Wall = of("constructedWall")
        val Rampart = of("rampart")
        val KeeperLair = of("keeperLair")
        val Portal = of("portal")
        val Controller = of("controller")
        val Link = of("link")
        val Storage = of("storage")
        val Tower = of("tower")
        val Observer = of("observer")
        val PowerBank = of("powerBank")
        val PowerSpawn = of("powerSpawn")
        val Extractor = of("extractor")
        val Lab = of("lab")
        val Terminal = of("terminal")
        val Container = of("container")
        val Nuker = of("nuker")
        val Factory = of("factory")
        val InvaderCore = of("invaderCore")
    }
}