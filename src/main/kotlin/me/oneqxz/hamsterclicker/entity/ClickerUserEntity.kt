package me.oneqxz.hamsterclicker.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


/**
 * @author 00101110001100010111000101111
 * @since 7/20/2024
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
data class ClickerUserEntity (
    var availableTaps: Long,
    var balanceCoins: Long,
    var totalConis: Long,
    var earnPerTap: Long,
    var maxTaps: Long,
    var tapsRecoverPerSec: Long
)