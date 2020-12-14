package com.sungbin.kakaoemoticonparser.util

import android.content.Context
import com.sungbin.androidutils.util.MediaUtil
import com.sungbin.androidutils.util.StorageUtil
import com.sungbin.androidutils.util.Util
import com.sungbin.kakaoemoticonparser.model.EmoticonData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object EmoticonUtil {

    fun getEmotionCode(html: String) =
        html.split("'\${IMAGE_URL}': \"")[1].split("dw/")[1].split(".")[0].toLong()

    @Throws(Exception::class)
    fun getEmoticonList(id: Long): ArrayList<String>? {
        var isBreak = false
        val list = ArrayList<String>()
        val suffix = if (id in 2211001..2230013) ".emot" else ".thum"
        return try {
            for (i in 1..100) {
                if (isBreak) break
                CoroutineScope(Dispatchers.IO).launch {
                    val address =
                        "https://item.kakaocdn.net/dw/$id${suffix}_${getDigitNum(i, 3)}.png"
                    val html = async { Util.getHtml(address) }
                    html.await()?.let {
                        if (it.contains("error") || it.contains("unsupported")) isBreak = true
                        else {
                            list.add(address)
                        }
                    }
                }
            }
            list
        } catch (exception: Exception) {
            throw exception
        }
    }

    private fun getDigitNum(number: Int, number2: Int) =
        ("0".repeat(number2 - number.toString().length)) + number.toString()

    fun download(context: Context, item: EmoticonData, url: String, index: Int) {
        val rootPath = "${StorageUtil.sdcard}/KakaoEmoticons/${item.title}"
        StorageUtil.createFolder("KakaoEmoticons/${item.title}")
        val path = "$rootPath/${item.originTitle}_$index.png"
        StorageUtil.createFile(path)
        DownloadUtil.download(path, url) { MediaUtil.scanning(context, path) }
    }
}