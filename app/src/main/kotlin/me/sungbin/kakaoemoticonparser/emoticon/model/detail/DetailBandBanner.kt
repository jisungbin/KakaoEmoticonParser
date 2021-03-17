package me.sungbin.kakaoemoticonparser.emoticon.model.detail

import com.google.gson.annotations.SerializedName

data class DetailBandBanner(

    @field:SerializedName("targetMobileLinkUrl")
    val targetMobileLinkUrl: String,

    @field:SerializedName("targetPcLinkUrl")
    val targetPcLinkUrl: String,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("bgColorCode")
    val bgColorCode: String
)
