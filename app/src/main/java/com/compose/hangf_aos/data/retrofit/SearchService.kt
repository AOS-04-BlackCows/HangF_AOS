package com.compose.hangf_aos.data.retrofit

import com.compose.hangf_aos.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchService {
    @GET("v2/local/search/address")
    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}")

    suspend fun getSearch(
        @Query("query") query : String,
        @Query("page") page : Int
    ): SearchResponse
}