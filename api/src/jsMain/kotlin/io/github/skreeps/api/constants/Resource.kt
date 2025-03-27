package io.github.skreeps.api.constants

interface Resource {
    companion object {
        private fun of(value: String) =
            value.unsafeCast<Resource>()

        val Energy = of("energy")
        val Power = of("power")

        val Hydrogen = of("H")
        val Oxygen = of("O")
        val Utrium = of("U")
        val Lemergium = of("L")
        val Keanium = of("K")
        val Zynthium = of("Z")
        val Catalyst = of("X")
        val Ghodium = of("G")

        val Silicon = of("silicon")
        val Metal = of("metal")
        val Biomass = of("biomass")
        val Mist = of("mist")

        val Hydroxide = of("OH")
        val ZynthiumKeanite = of("ZK")
        val UtriumLemergite = of("UL")

        val UtriumHydride = of("UH")
        val UtriumOxide = of("UO")
        val KeaniumHydride = of("KH")
        val KeaniumOxide = of("KO")
        val LemergiumHydride = of("LH")
        val LemergiumOxide = of("LO")
        val ZynthiumHydride = of("ZH")
        val ZynthiumOxide = of("ZO")
        val GhodiumHydride = of("GH")
        val GhodiumOxide = of("GO")

        val UtriumAcid = of("UH2O")
        val UtriumAlkalide = of("UHO2")
        val KeaniumAcid = of("KH2O")
        val KeaniumAlkalide = of("KHO2")
        val LemergiumAcid = of("LH2O")
        val LemergiumAlkalide = of("LHO2")
        val ZynthiumAcid = of("ZH2O")
        val ZynthiumAlkalide = of("ZHO2")
        val GhodiumAcid = of("GH2O")
        val GhodiumAlkalide = of("GHO2")

        val CatalyzedUtriumAcid = of("XUH2O")
        val CatalyzedUtriumAlkalide = of("XUHO2")
        val CatalyzedKeaniumAcid = of("XKH2O")
        val CatalyzedKeaniumAlkalide = of("XKHO2")
        val CatalyzedLemergiumAcid = of("XLH2O")
        val CatalyzedLemergiumAlkalide = of("XLHO2")
        val CatalyzedZynthiumAcid = of("XZH2O")
        val CatalyzedZynthiumAlkalide = of("XZHO2")
        val CatalyzedGhodiumAcid = of("XGH2O")
        val CatalyzedGhodiumAlkalide = of("XGHO2")

        val Ops = of("ops")

        val UtriumBar = of("utrium_bar")
        val LemergiumBar = of("lemergium_bar")
        val ZynthiumBar = of("zynthium_bar")
        val KeaniumBar = of("keanium_bar")
        val GhodiumMelt = of("ghodium_melt")
        val Oxidant = of("oxidant")
        val Reductant = of("reductant")
        val Purifier = of("purifier")
        val Battery = of("battery")

        val Composite = of("composite")
        val Crystal = of("crystal")
        val Liquid = of("liquid")

        val Wire = of("wire")
        val Switch = of("switch")
        val Transistor = of("transistor")
        val Microchip = of("microchip")
        val Circuit = of("circuit")
        val Device = of("device")

        val Cell = of("cell")
        val Phlegm = of("phlegm")
        val Tissue = of("tissue")
        val Muscle = of("muscle")
        val Organoid = of("organoid")
        val Organism = of("organism")

        val Alloy = of("alloy")
        val Tube = of("tube")
        val Fixtures = of("fixtures")
        val Frame = of("frame")
        val Hydraulics = of("hydraulics")
        val Machine = of("machine")

        val Condensate = of("condensate")
        val Concentrate = of("concentrate")
        val Extract = of("extract")
        val Spirit = of("spirit")
        val Emanation = of("emanation")
        val Essence = of("essence")
    }

    interface Intershard {
        companion object {
            private fun of(value: String) =
                value.unsafeCast<Intershard>()

            val SubscriptionToken = of("token")
            val CpuUnlock = of("cpuUnlock")
            val Pixel = of("pixel")
            val AccessKey = of("accessKey")
        }
    }
}