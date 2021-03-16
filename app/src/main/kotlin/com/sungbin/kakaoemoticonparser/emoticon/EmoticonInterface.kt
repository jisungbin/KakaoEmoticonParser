package com.sungbin.kakaoemoticonparser.emoticon

import com.sungbin.kakaoemoticonparser.emoticon.model.Response
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface EmoticonInterface {
    @GET("api/v1/search")
    fun getSearchData(
        @Query("query") query: String
    ): Flowable<Response>
}
