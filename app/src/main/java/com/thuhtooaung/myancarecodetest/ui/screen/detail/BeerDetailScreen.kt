package com.thuhtooaung.myancarecodetest.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.thuhtooaung.myancarecodetest.data.remote.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerDetailScreen(
    modifier: Modifier = Modifier,
    myViewModel: BeerDetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {

    val uiState = myViewModel.beer.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.value.beerDto?.name ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { scaffoldPadding ->

        when (uiState.value.status) {
            Status.Loading -> {
                Column(
                    modifier = Modifier
                        .padding(scaffoldPadding)
                        .fillMaxSize()
                        .wrapContentSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            Status.Error -> {
                Column(
                    modifier = Modifier
                        .padding(scaffoldPadding)
                        .fillMaxSize()
                        .wrapContentSize()
                ) {
                    TextButton(onClick = { myViewModel.getBeerById() }) {
                        Text(text = "Refresh")
                    }
                }
            }

            Status.Success -> {
                LazyColumn(
                    modifier = modifier
                        .padding(scaffoldPadding)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    item {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth(),
                            model = uiState.value.beerDto?.image_url,
                            contentDescription = null
                        )
                    }

                    item {
                        Text(
                            text = uiState.value.beerDto?.name ?: "",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    item {
                        Text(
                            text = uiState.value.beerDto?.tagline ?: "",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }

                    item {
                        Text(
                            text = uiState.value.beerDto?.description ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            }
        }
    }

}