package me.sungbin.kakaoemoticonparser.emoticon.model.detail

import com.google.gson.annotations.SerializedName

data class RelatedItemInfosItem(

    @field:SerializedName("titleImageUrl")
    val titleImageUrl: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("titleUrl")
    val titleUrl: String
)
