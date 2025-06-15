package com.compose.hangf_aos.data.repository.repoInterfaces

import com.compose.hangf_aos.data.retrofit.SearchResponse

interface SearchRepository {
    suspend fun requestSearch(query : String) : SearchResponse
}