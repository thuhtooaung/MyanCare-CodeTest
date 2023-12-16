package com.thuhtooaung.myancarecodetest.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.thuhtooaung.myancarecodetest.data.local.BeerEntity
import com.thuhtooaung.myancarecodetest.repository.database.BeerDatabase
import com.thuhtooaung.myancarecodetest.repository.network.ApiHelper
import com.thuhtooaung.myancarecodetest.repository.remote.BeerRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @OptIn(ExperimentalPagingApi::class)
    @Singleton
    @Provides
    fun provideBeerPager(beerDatabase: BeerDatabase, apiHelper: ApiHelper): Pager<Int, BeerEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = BeerRemoteMediator(
                beerDatabase = beerDatabase,
                apiHelper = apiHelper
            ),
            pagingSourceFactory = {
                beerDatabase.dao.pagingSource()
            }
        )
    }
}