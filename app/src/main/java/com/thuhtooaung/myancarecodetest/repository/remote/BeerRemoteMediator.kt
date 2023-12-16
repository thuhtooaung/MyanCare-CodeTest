package com.thuhtooaung.myancarecodetest.repository.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.thuhtooaung.myancarecodetest.data.local.BeerEntity
import com.thuhtooaung.myancarecodetest.data.mapper.toBeerEntity
import com.thuhtooaung.myancarecodetest.repository.database.BeerDatabase
import com.thuhtooaung.myancarecodetest.repository.network.ApiHelper

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerDatabase: BeerDatabase,
    private val apiHelper: ApiHelper
) : RemoteMediator<Int, BeerEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    Log.d("LITEM", lastItem.toString())
                    lastItem ?: return MediatorResult.Success(endOfPaginationReached = true)
                    if (lastItem.id % 20 == 0) {
                        (lastItem.id / state.config.pageSize) + 1
                    } else {
                        (lastItem.id / state.config.pageSize) + 2
                    }
                }
            }
            val beers = apiHelper.getBeers(page = loadKey, size = state.config.pageSize)
            beerDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    beerDatabase.dao.clearAll()
                }
                val beerEntities = beers.map { it.toBeerEntity() }
                beerDatabase.dao.upsertAll(beerEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = beers.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}