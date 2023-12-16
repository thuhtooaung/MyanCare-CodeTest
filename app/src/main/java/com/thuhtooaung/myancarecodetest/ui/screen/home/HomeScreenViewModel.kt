package com.thuhtooaung.myancarecodetest.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.thuhtooaung.myancarecodetest.data.local.BeerEntity
import com.thuhtooaung.myancarecodetest.data.mapper.toBeer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    pager: Pager<Int, BeerEntity>
) : ViewModel() {

    val beers = pager.flow.map { pagingData ->
        pagingData.map { it.toBeer() }
    }.cachedIn(viewModelScope)

}