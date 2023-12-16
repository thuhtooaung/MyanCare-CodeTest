package com.thuhtooaung.myancarecodetest.repository.network

import com.thuhtooaung.myancarecodetest.data.remote.BeerDto
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {

    override suspend fun getBeers(page: Int, size: Int): List<BeerDto> =
        apiService.getBeers(page, size)

    override suspend fun getBeer(id: Int): List<BeerDto> = apiService.getBeer(id)

}