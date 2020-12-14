package com.sungbin.kakaoemoticonparser.util

import com.sungbin.kakaoemoticonparser.model.EmoticonData
import org.json.JSONObject
import org.jsoup.Jsoup

object ParseUtil {

    const val MOBILE_USER_AGENT =
        "Mozilla/5.0 (Linux; Android 10; SM-G977N Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/84.0.4147.125 Mobile Safari/537.36;KAKAOTALK 2108970"

    fun getHtml(address: String) =
        Jsoup.connect(address).userAgent(MOBILE_USER_AGENT).get().toString()

    fun getSearchedData(json: String): ArrayList<EmoticonData>? {
        return try {
            val data = ArrayList<EmoticonData>()
            val jsonArray = JSONObject(json).getJSONObject("result").getJSONArray("content")

            for (i in 0 until jsonArray.length()) {
                val content = jsonArray.getJSONObject(i)

                val artist = content.getString("artist")
                val haveSound = content.getBoolean("isSound")
                val isBig = content.getBoolean("isBigEmo")
                val title = content.getString("title")
                val originTitle = content.getString("titleUrl")
                val url = "https://e.kakao.com/t/$originTitle"
                val thumbnailUrl = content.getString("titleDetailUrl")
                val item = EmoticonData(
                    title,
                    artist,
                    url,
                    thumbnailUrl,
                    isBig,
                    haveSound,
                    originTitle
                )

                data.add(item)
            }

            if (data.isEmpty()) null
            else data
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

}