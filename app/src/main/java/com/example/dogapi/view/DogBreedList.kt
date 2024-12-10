package com.example.dogapi.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.dogapi.viewModel.DogViewModel

@Composable
fun DogBreedList(viewModel: DogViewModel = hiltViewModel()) {
    val filteredBreeds by viewModel.filteredBreeds.collectAsState(initial = emptyList())
    val randomImages by viewModel.randomImages.collectAsState(initial = emptyMap())
    val favorites by viewModel.favorite.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(filteredBreeds.size) { index ->
            val breed = filteredBreeds[index]
            val breedImage = randomImages["${breed.breed}_"] ?: ""
            val isBreedFavorite = favorites.any { it.id == breed.breed }

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = rememberAsyncImagePainter(model = breedImage),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(end = 16.dp)
                        )
                        Text(
                            text = breed.breed.capitalize(),
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.weight(1f)
                        )
                        FavoriteButton(isFavorite = isBreedFavorite,
                            onToggle = {
                                viewModel.toggleFavorite(breed.breed,breed.breed, isSubBreed = false)
                            }
                        )
                    }

                    if (breed.subBreeds.isNotEmpty()) {
                        Text(
                            text = "Sub-breeds:",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(breed.subBreeds) { subBread ->
                                val subBreedImage = randomImages["${breed.breed}_$subBread"] ?: ""
                                val subBreadId = "${breed.breed}_$subBread"
                                val isSubBreadFavorite = favorites.any{ it.id == subBreadId }
                                Card(
                                    modifier = Modifier.size(150.dp),
                                    elevation = 4.dp
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        Image(
                                            painter = rememberAsyncImagePainter(model = subBreedImage),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(100.dp)
                                        )
                                        Text(
                                            text = subBread.capitalize(),
                                            style = MaterialTheme.typography.body2,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                        FavoriteButton(
                                            isFavorite = isSubBreadFavorite,
                                            onToggle = {
                                                viewModel.toggleFavorite(subBreadId,"$subBread${breed.breed}", isSubBreed = true)
                                            }
                                        )
                                    }
                                    LaunchedEffect(subBread) {
                                        viewModel.fetchRandomImage(breed.breed, subBread)
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}