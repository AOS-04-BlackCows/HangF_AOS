package com.compose.hangf_aos.data.repository.repoImpl

import com.compose.hangf_aos.data.repository.repoInterfaces.SearchRepository
import com.compose.hangf_aos.data.retrofit.SearchResponse
import com.compose.hangf_aos.data.retrofit.SearchService
import javax.inject.Inject
import javax.inject.Named

class SearchRepositoryImpl @Inject constructor(
    @Named("SearchService") private val searchService: SearchService)
    : SearchRepository {
        override suspend fun requestSearch(query: String): SearchResponse {
            return searchService.getSearch(query = query, page = 1)
        }
}