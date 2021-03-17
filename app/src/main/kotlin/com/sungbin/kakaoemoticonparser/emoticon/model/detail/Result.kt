package com.sungbin.kakaoemoticonparser.emoticon.model.detail

import com.google.gson.annotations.SerializedName

data class Result(

    @field:SerializedName("selectedFriend")
    val selectedFriend: Any,

    @field:SerializedName("isLike")
    val isLike: Boolean,

    @field:SerializedName("artist")
    val artist: String,

    @field:SerializedName("detailMainImageUrl")
    val detailMainImageUrl: Any,

    @field:SerializedName("isPackage")
    val isPackage: Boolean,

    @field:SerializedName("isNew")
    val isNew: Boolean,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("giftImageUrl")
    val giftImageUrl: String,

    @field:SerializedName("version")
    val version: String,

    @field:SerializedName("titleDetailUrl")
    val titleDetailUrl: String,

    @field:SerializedName("isBigEmo")
    val isBigEmo: Boolean,

    @field:SerializedName("usableTicket")
    val usableTicket: UsableTicket,

    @field:SerializedName("detailBandBanner")
    val detailBandBanner: DetailBandBanner,

    @field:SerializedName("shareImageUrl")
    val shareImageUrl: String,

    @field:SerializedName("isOpen")
    val isOpen: Boolean,

    @field:SerializedName("isSound")
    val isSound: Boolean,

    @field:SerializedName("isSdkAgree")
    val isSdkAgree: Boolean,

    @field:SerializedName("price")
    val price: Int,

    @field:SerializedName("relatedItemInfos")
    val relatedItemInfos: List<RelatedItemInfosItem>,

    @field:SerializedName("titleImageUrl")
    val titleImageUrl: String,

    @field:SerializedName("thumbnailUrls")
    val thumbnailUrls: List<String>,

    @field:SerializedName("titleUrl")
    val titleUrl: String
)
