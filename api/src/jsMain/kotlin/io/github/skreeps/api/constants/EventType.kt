package io.github.skreeps.api.constants

typealias EventType = Int

object Events {
    const val Attack = 1
    const val ObjectDestroyed = 2
    const val AttackController = 3
    const val Build = 4
    const val Harvest = 5
    const val Heal = 6
    const val Repair = 7
    const val ReserveController = 8
    const val UpgradeController = 9
    const val Exit = 10
    const val Power = 11
    const val Transfer = 12
    const val AttackTypeMelee = 1
    const val AttackTypeRanged = 2
    const val AttackTypeRangedMass = 3
    const val AttackTypeDismantle = 4
    const val AttackTypeHitBack = 5
    const val AttackTypeNuke = 6
    const val HealTypeMelee = 1
    const val HealTypeRanged = 2
}
