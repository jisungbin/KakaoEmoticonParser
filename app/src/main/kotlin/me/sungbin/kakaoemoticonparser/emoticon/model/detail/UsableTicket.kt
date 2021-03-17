package me.sungbin.kakaoemoticonparser.emoticon.model.detail

import com.google.gson.annotations.SerializedName

data class UsableTicket(

    @field:SerializedName("ticketName")
    val ticketName: String,

    @field:SerializedName("saleFactor")
    val saleFactor: Double
)
