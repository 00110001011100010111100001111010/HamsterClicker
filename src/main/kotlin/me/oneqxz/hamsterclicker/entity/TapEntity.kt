package me.oneqxz.hamsterclicker.entity

import java.time.Instant

data class TapEntity (
    val availableTaps: Long,
    val count: Long,
    val timestamp: Long = Instant.now().toEpochMilli()
)
