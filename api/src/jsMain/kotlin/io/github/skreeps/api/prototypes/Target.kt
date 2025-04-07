package io.github.skreeps.api.prototypes

sealed external interface HarvestTarget

sealed external interface AttackTarget

sealed external interface TransferTarget

sealed external interface WithdrawTarget

sealed external interface CreepTarget : AttackTarget, TransferTarget
