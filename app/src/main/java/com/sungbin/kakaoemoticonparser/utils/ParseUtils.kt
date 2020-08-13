package com.sungbin.kakaoemoticonparser.utils

import com.google.gson.JsonObject
import com.sungbin.kakaoemoticonparser.model.EmoticonData
import com.sungbin.kakaoemoticonparser.utils.extensions.parse
import com.sungbin.sungbintool.LogUtils
import org.json.JSONObject

object ParseUtils {

    fun getSearchedData(html: String): ArrayList<EmoticonData>? {
        val data = ArrayList<EmoticonData>()
        return try {
            val json = html.replace("&quot;", "\"")
            val jsonContent = json.parse("SearchPage", "SearchPage-0", 1, false)
                .split("\" data-react-props=\"")[1]
                .split("\" data-react-cache-id=\"")[0]

            val jsonObject = JSONObject(jsonContent).getJSONArray("items")
            for (i in 0 until jsonObject.length()) {
                val content = jsonObject.getJSONObject(i)
                val artist = content.getString("artist")
                val haveMotion: Boolean
                val haveSound: Boolean
                content.getJSONObject("icon").run {
                    haveMotion = getBoolean("motion")
                    haveSound = getBoolean("sound")
                }
                val isBig = content.getBoolean("isBigEmo")
                val title = content.getString("title")
                val url = "https://e.kakao.com/t/${content["titleUrl"]}"
                val thumbnailUrl = content.getString("titleDetailUrl")
                val item = EmoticonData(title, artist, url, thumbnailUrl, isBig, haveMotion, haveSound, 1/*getEmoticonCode(url)*/)
                data.add(item)
            }
            data
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

   /* private fun getEmoticonCode(link: String) =
        link.split("data-item-code=\"")[2].split("\"")[0].toInt()*/
}