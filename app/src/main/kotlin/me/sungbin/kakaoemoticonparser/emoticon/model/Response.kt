package me.sungbin.kakaoemoticonparser.emoticon.model

import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("result")
    val result: Result,

    @field:SerializedName("status")
    val status: Int
)
