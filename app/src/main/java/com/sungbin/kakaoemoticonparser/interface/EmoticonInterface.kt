package com.sungbin.kakaoemoticonparser.`interface`

import io.reactivex.rxjava3.core.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by SungBin on 2020-08-10.
 */

interface EmoticonInterface {

    @GET("search")
    fun getSearchData(
        @Query("q") query: String
    ): Flowable<ResponseBody>


}