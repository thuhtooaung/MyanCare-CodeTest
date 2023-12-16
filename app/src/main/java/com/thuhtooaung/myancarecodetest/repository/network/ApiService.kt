package com.thuhtooaung.myancarecodetest.repository.network

import com.thuhtooaung.myancarecodetest.data.remote.BeerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ): List<BeerDto>

    @GET("beers/{id}")
    suspend fun getBeer(
        @Path("id") id: Int
    ): List<BeerDto>

}