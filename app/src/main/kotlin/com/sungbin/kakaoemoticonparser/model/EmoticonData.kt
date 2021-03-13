package com.sungbin.kakaoemoticonparser.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.sungbin.kakaoemoticonparser.module.GlideApp

/**
 * Created by SungBin on 2020-08-13.
 */

data class EmoticonData(
    val title: String,
    val artist: String,
    val url: String,
    var thumbnailUrl: String,
    val isBig: Boolean,
    val haveSound: Boolean,
    var originTitle: String
) {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(imageView: ImageView, url: String?) {
            GlideApp
                .with(imageView.context)
                .load(url ?: "")
                .into(imageView)
        }
    }
}
