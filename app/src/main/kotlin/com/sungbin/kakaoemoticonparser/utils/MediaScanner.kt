package com.sungbin.kakaoemoticonparser.utils

import android.content.Context
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.MediaScannerConnectionClient
import android.net.Uri

class MediaScanner constructor(private val ctx: Context) {

    fun mediaScanning(path: String) {
        var mediaScanner: MediaScannerConnection? = null
        val mediaScannerClient = object : MediaScannerConnectionClient {
            override fun onMediaScannerConnected() {
                mediaScanner?.scanFile(path, null)
            }

            override fun onScanCompleted(
                path: String,
                uri: Uri
            ) {
                mediaScanner?.disconnect()
            }
        }

        mediaScanner = MediaScannerConnection(ctx, mediaScannerClient)
        mediaScanner.connect()
    }

    companion object {
        fun instance(context: Context) = MediaScanner(context)
    }

}