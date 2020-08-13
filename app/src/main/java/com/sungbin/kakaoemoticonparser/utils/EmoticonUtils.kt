package com.sungbin.kakaoemoticonparser.utils

import com.sungbin.sungbintool.LogUtils
import com.sungbin.sungbintool.Utils

object EmoticonUtils {

    fun getEmoticonList(id: Long): ArrayList<String>? {
        val list = ArrayList<String>()
        val suffix = if (id in 2211001..2230013) ".emot" else ".thum"
        return try {
            for (i in 1..100) {
                val address = "https://item.kakaocdn.net/dw/$id${suffix}_${getDigitNum(i, 3)}.png"
                val html = Utils.getHtml(address) ?: "error"
                if (html.contains("error") || html.contains("unsupported")) break
                else {
                    list.add(address)
                }
            }
            list
        } catch (ignored: Exception) {
            null
        }
    }

    private fun getDigitNum(number1: Int, number2: Int) =
        ("0".repeat(number2 - number1.toString().length)) + number1.toString()

}