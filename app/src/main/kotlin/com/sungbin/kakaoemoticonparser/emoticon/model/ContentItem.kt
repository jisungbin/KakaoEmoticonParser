package com.sungbin.kakaoemoticonparser.emoticon.model

import com.google.gson.annotations.SerializedName

data class ContentItem(

    @field:SerializedName("isLike")
    val isLike: Boolean,

    @field:SerializedName("isSound")
    val isSound: Boolean,

    @field:SerializedName("artist")
    val artist: String,

    @field:SerializedName("isToday")
    val isToday: Boolean,

    @field:SerializedName("titleImageUrl")
    val titleImageUrl: String,

    @field:SerializedName("isPackage")
    val isPackage: Boolean,

    @field:SerializedName("isNew")
    val isNew: Boolean,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("isOnSale")
    val isOnSale: Boolean,

    @field:SerializedName("titleDetailUrl")
    val titleDetailUrl: String,

    @field:SerializedName("titleUrl")
    val titleUrl: String,

    @field:SerializedName("isBigEmo")
    val isBigEmo: Boolean
)
