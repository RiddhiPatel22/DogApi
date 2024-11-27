package com.example.dogapi.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.dogapi.viewModel.DogViewModel


@Composable
fun FavoriteCarousel(viewModel: DogViewModel = hiltViewModel()) {

    val randomImages by viewModel.randomImages.collectAsState(initial = emptyMap())
    val favorites by viewModel.favorite.collectAsState(initial = emptyList())

    if (favorites.isNotEmpty()) {
        // Group favorites by main breed
        val groupedFavorites = favorites.groupBy { favorite ->
            if (favorite.isSubBread) favorite.id.split("_")[0] else favorite.id
        }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                groupedFavorites.forEach { (mainBreed, items) ->
                    // Check if the main breed itself is favorite
                    val isMainBreedFavorite = items.any { !it.isSubBread }

                    // Filter only favorite sub-breeds
                    val favoriteSubBreeds = items.filter { it.isSubBread }

                    item {
                        Card(
                            modifier = Modifier
                                .width(300.dp)
                                .padding(8.dp),
                            elevation = 8.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                // Main Breed Image and Name
                                val mainBreedImage = randomImages["${mainBreed}_"] ?: ""
                                Image(
                                    painter = rememberAsyncImagePainter(model = mainBreedImage),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                )
                                Text(
                                    text = "Main Breed: ${mainBreed.capitalize()}",
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier.padding(4.dp)
                                )

                                // Show only favorite sub-breeds if they exist
                                if (favoriteSubBreeds.isNotEmpty()) {
                                    Text(
                                        text = "Sub-breeds:",
                                        style = MaterialTheme.typography.subtitle2,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                    favoriteSubBreeds.forEach { subBreedFavorite ->
                                        val subBreedName =
                                            subBreedFavorite.id.split("_")[1].capitalize()
                                        val subBreedImage = randomImages[subBreedFavorite.id] ?: ""
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = rememberAsyncImagePainter(model = subBreedImage),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(100.dp)
                                                    .padding(end = 8.dp)
                                            )
                                            Text(
                                                text = subBreedName,
                                                style = MaterialTheme.typography.body2,
                                                modifier = Modifier.weight(1f)
                                            )
                                            IconButton(
                                                onClick = {
                                                    viewModel.toggleFavorite(
                                                        id = subBreedFavorite.id,
                                                        name = subBreedFavorite.name,
                                                        isSubBreed = true
                                                    )
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Favorite,
                                                    contentDescription = "Remove Sub-breed Favorite",
                                                    tint = Color.Red
                                                )
                                            }
                                        }
                                    }
                                }

                                // Favorite Button for Main Breed
                                if (isMainBreedFavorite) {
                                    IconButton(
                                        onClick = {
                                            viewModel.toggleFavorite(
                                                id = mainBreed,
                                                name = mainBreed,
                                                isSubBreed = false
                                            )
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = "Remove Main Breed Favorite",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
    } else {
        Text(
            text = "No favorites yet!",
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}