package com.sungbin.kakaoemoticonparser.emoticon.model

import com.google.gson.annotations.SerializedName

data class ContentItem(

    @field:SerializedName("isLike")
    val isLike: Boolean? = null,

    @field:SerializedName("isSound")
    val isSound: Boolean? = null,

    @field:SerializedName("artist")
    val artist: String? = null,

    @field:SerializedName("isToday")
    val isToday: Boolean? = null,

    @field:SerializedName("titleImageUrl")
    val titleImageUrl: String? = null,

    @field:SerializedName("isPackage")
    val isPackage: Boolean? = null,

    @field:SerializedName("isNew")
    val isNew: Boolean? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("isOnSale")
    val isOnSale: Boolean? = null,

    @field:SerializedName("titleDetailUrl")
    val titleDetailUrl: String? = null,

    @field:SerializedName("titleUrl")
    val titleUrl: String? = null,

    @field:SerializedName("isBigEmo")
    val isBigEmo: Boolean? = null
)
