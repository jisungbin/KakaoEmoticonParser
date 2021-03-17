package me.sungbin.kakaoemoticonparser.emoticon

import me.sungbin.kakaoemoticonparser.emoticon.model.detail.Response as DetailResponse
import me.sungbin.kakaoemoticonparser.emoticon.model.Response
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EmoticonInterface {
    @GET("api/v1/search")
    fun getSearchData(
        @Query("query") query: String
    ): Flowable<Response>

    @GET("api/v1/items/t/{name}")
    fun getDetailData(
        @Path("name") name: String
    ): Flowable<DetailResponse>
}
