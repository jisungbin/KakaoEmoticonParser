package com.sungbin.kakaoemoticonparser

import android.app.Application
import android.content.Context
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
    }
}