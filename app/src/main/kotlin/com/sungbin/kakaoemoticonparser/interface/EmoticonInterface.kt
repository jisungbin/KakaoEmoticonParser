package com.sungbin.kakaoemoticonparser.`interface`

import io.reactivex.rxjava3.core.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by SungBin on 2020-08-10.
 */

interface EmoticonInterface {

    @GET("api/v1/search")
    fun getSearchData(
        @Query("query") query: String
    ): Flowable<ResponseBody>

    // https://e.kakao.com/t/affection-will-blow-your-mind
    @GET("{title}")
    fun getCodeData(
        @Path("title") title: String
    ): Flowable<ResponseBody>
}
