package com.thuhtooaung.myancarecodetest.repository.network

import com.thuhtooaung.myancarecodetest.data.remote.BeerDto

interface ApiHelper {

    suspend fun getBeers(page: Int, size: Int): List<BeerDto>

    suspend fun getBeer(id: Int): List<BeerDto>

}