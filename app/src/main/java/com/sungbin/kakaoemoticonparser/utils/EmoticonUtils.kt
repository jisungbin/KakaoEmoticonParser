package com.sungbin.kakaoemoticonparser.utils

import android.os.AsyncTask
import com.sungbin.kakaoemoticonparser.model.EmoticonData
import com.sungbin.sungbintool.StorageUtils
import com.sungbin.sungbintool.Utils
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object EmoticonUtils {

    fun getEmoticonList(id: Long): ArrayList<String>? {
        val list = ArrayList<String>()
        val suffix = if (id in 2211001..2230013) ".emot" else ".thum"
        return try {
            for (i in 1..100) {
                val address = "https://item.kakaocdn.net/dw/$id${suffix}_${getDigitNum(i, 3)}.png"
                val html = Utils.getHtml(address) ?: break
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

    fun download(item: EmoticonData, url: String, index: Int) {
        val rootPath = "${StorageUtils.sdcard}/KakaoEmoticons/${item.title}"
        StorageUtils.createFolder("KakaoEmoticons/${item.title}")
        val path = "$rootPath/${item.originTitle}_$index.png"
        File(path).createNewFile()
        ImageDownloadTask().execute(path, url)
    }

    private class ImageDownloadTask : AsyncTask<String?, Void?, Void?>() {
        override fun doInBackground(vararg params: String?): Void? {
            try {
                val conn = URL(params[1]).openConnection() as HttpURLConnection
                val len = conn.contentLength
                val tmpByte = ByteArray(len)
                val `is` = conn.inputStream
                val fos = FileOutputStream(params[0])

                while (true) {
                    val read = `is`.read(tmpByte)
                    if (read <= 0) {
                        break
                    }
                    fos.write(tmpByte, 0, read)
                }

                `is`.close()
                fos.close()
                conn.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            return
        }

    }
}