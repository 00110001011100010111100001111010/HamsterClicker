package me.oneqxz.hamsterclicker

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.net.InetSocketAddress
import java.net.Proxy


/**
 * @author 00101110001100010111000101111
 * @since 7/20/2024
 **/

class ProxyProvider (
    private val source: String
) {

    override fun toString(): String {
        return "ProxyProvider(proxy=$proxy, username=$username, password=$password)"
    }

    val proxy: Proxy

    val username: String?
        get() = source.split(":").getOrNull(3)

    val password: String?
        get() = source.split(":").getOrNull(4)

    init {
        val parts = source.split(":")
        proxy = Proxy (
            Proxy.Type.valueOf(parts[0]),
            InetSocketAddress(parts[1], parts[2].toInt())
        )
    }

}

data class Requests (
    @JsonSetter(nulls = Nulls.AS_EMPTY) val headers: Map<String, String>,
    @JsonSetter(nulls = Nulls.AS_EMPTY) val proxies: List<ProxyProvider>
)

data class Hamster (
    val token: String
)

data class Clicker (
    val wait: List<Double>,
    val taps: List<Int>
)

data class Config (
    val hamster: Hamster,
    val requests: Requests,
    val clicker: Clicker
)

object ConfigProvider {

    val config: Config

    init {
        val mapper = YAMLMapper().apply {
            registerKotlinModule()
        }

        val configFile = File("config.yml")
        config = mapper.readValue(configFile, Config::class.java)
    }
}