package com.thuhtooaung.myancarecodetest.ui.screen.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thuhtooaung.myancarecodetest.data.remote.BeerDto
import com.thuhtooaung.myancarecodetest.data.remote.Status
import com.thuhtooaung.myancarecodetest.repository.network.ApiHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "BeerDetailViewModel"

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiHelper: ApiHelper
) : ViewModel() {

    private val beerId: Int = checkNotNull(savedStateHandle["id"])

    private val _beer = MutableStateFlow(BeerDetailUiState())
    val beer: StateFlow<BeerDetailUiState> get() = _beer.asStateFlow()

    init {
        getBeerById()
    }

    fun getBeerById() {
        viewModelScope.launch {
            try {
                _beer.value = BeerDetailUiState(status = Status.Loading)
                val response = apiHelper.getBeer(beerId)
                _beer.value = BeerDetailUiState(
                    status = Status.Success,
                    beerDto = response.first()
                )
            } catch (e: Exception) {
                _beer.value = BeerDetailUiState(
                    status = Status.Error,
                    errorMessage = ""
                )
                Log.d(TAG, e.toString())
            }
        }
    }

}

data class BeerDetailUiState(
    val status: Status = Status.Loading,
    val beerDto: BeerDto? = null,
    val errorMessage: String? = null
)