package com.sungbin.kakaoemoticonparser.emoticon.model

import com.google.gson.annotations.SerializedName

data class Result(

    @field:SerializedName("last")
    val last: Boolean,

    @field:SerializedName("totalCount")
    val totalCount: Int,

    @field:SerializedName("content")
    val content: List<ContentItem>
)
