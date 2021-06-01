package com.dakuo


import com.google.gson.Gson
import okhttp3.*


class HuobiService {
    fun getDetail(symbol: String): ResultBean? {
        val client = OkHttpClient()
        val request = Request.Builder().url("https://api.huobi.de.com/market/history/kline?period=1day&size=1&symbol=$symbol").get().build()
        val call = client.newCall(request)
        val string = call.execute().body?.string()
        return Gson().fromJson(string, ResultBean::class.java)
    }

    data class ResultBean(
        val ch: String,
        val status: String,
        val ts: String,
        val data: List<ticker>
    )

    data class ticker(
        val id: String,
        val open: Double,
        val close: Double,
        val low: Double,
        val high: Double,
        val amount: Double,
        val vol: String,
        val count: Int
    )
}



