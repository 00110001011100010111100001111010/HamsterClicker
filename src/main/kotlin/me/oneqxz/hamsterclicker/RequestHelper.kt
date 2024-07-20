package me.oneqxz.hamsterclicker

import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request

object RequestHelper {

    fun baseRequest(): Request.Builder {
        val builder = Request.Builder()

        builder.addHeader("Authorization", "Bearer ${ConfigProvider.config.hamster.token}")

        for ((key, value) in ConfigProvider.config.requests.headers) {
            builder.addHeader(key, value)
        }

        return builder
    }

    fun baseClient(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()

        val proxy: ProxyProvider? = Utils.getRandomElementOrNull(ConfigProvider.config.requests.proxies)
        builder.proxy(proxy?.proxy)

        if(proxy?.password != null && proxy.username != null)
        {
            builder.proxyAuthenticator { _, response ->
                val credits = Credentials.basic(
                    proxy.username!!,
                    proxy.password!!
                )

                response.request.newBuilder()
                    .header("Proxy-Authorization", credits)
                    .build()
            }
        }

        return builder
    }
}