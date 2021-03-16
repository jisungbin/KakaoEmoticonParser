package com.sungbin.kakaoemoticonparser.emoticon.model

import com.google.gson.annotations.SerializedName

data class Result(

    @field:SerializedName("last")
    val last: Boolean? = null,

    @field:SerializedName("totalCount")
    val totalCount: Int? = null,

    @field:SerializedName("content")
    val content: List<ContentItem?>? = null
)
