package me.oneqxz.hamsterclicker

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import me.oneqxz.hamsterclicker.entity.ClickerUserEntity
import me.oneqxz.hamsterclicker.entity.TapEntity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.lang.Thread.sleep

/**
 * @author 00101110001100010111000101111
 * @since 7/20/2024
 **/

fun main(args: Array<String>) {


    while(true) {
        val user = getClickerUser()

        val tapModifier = (Utils.getRandomElementOrNull(ConfigProvider.config.clicker.taps) ?: 10).coerceIn(0, 100)
        val wait = Utils.getRandomElementOrNull(ConfigProvider.config.clicker.wait) ?: 5.0

        val tapped = (user.availableTaps * (tapModifier / 100.0)).toLong()
        makeTap(tapped, user.availableTaps)

        println("Tap! $tapped (mod: $tapModifier), waiting: ${wait}s")
        sleep((wait * 1000).toLong())
    }

}

fun getClickerUser(): ClickerUserEntity {

    val request = RequestHelper.baseRequest()
        .url("https://api.hamsterkombatgame.io/clicker/sync")
        .post("".toRequestBody())
        .build()

    try {
        val response = RequestHelper.baseClient().build().newCall(request).execute()

        if(response.code != 200)
            throw IllegalStateException("Invalid auth token, or server down")

        val responseBody = response.body.string()
        val mapper = jacksonObjectMapper()

        val rootNode: JsonNode = mapper.readTree(responseBody)
        val clickerUser: JsonNode = rootNode.get("clickerUser")

        return mapper.treeToValue(clickerUser, ClickerUserEntity::class.java)

    } catch (e: IOException) {
        throw e
    }
}

fun makeTap(value: Number, available: Number) {

    val data = TapEntity(
        availableTaps = available.toLong() - value.toLong(),
        count = value.toLong()
    )

    val request = RequestHelper.baseRequest()
        .url("https://api.hamsterkombatgame.io/clicker/tap")
        .post (
            jacksonObjectMapper()
                .writeValueAsString(data)
                .toRequestBody("application/json; charset=utf-8".toMediaType())
        )
        .build()

    try {
        val resp = RequestHelper.baseClient().build().newCall(request).execute()
    }  catch (e: IOException) {
        e.printStackTrace()
    }
}