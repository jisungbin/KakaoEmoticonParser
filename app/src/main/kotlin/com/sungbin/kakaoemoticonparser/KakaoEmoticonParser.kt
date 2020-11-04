package com.sungbin.kakaoemoticonparser

import android.app.Application
import android.content.Context
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp


/**
 * Created by SungBin on 2020-08-13.
 */

@HiltAndroidApp
class KakaoEmoticonParser : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build() // todo: 이게 bad-way 였다니...
        StrictMode.setThreadPolicy(policy)
    }
}
