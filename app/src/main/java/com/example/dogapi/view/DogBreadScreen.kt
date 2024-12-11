package com.example.dogapi.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dogapi.viewModel.DogViewModel
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun DogBreedsScreen(viewModel: DogViewModel = hiltViewModel()){

    val isShowingFavourites by viewModel.isShowingFavorites.collectAsState()

    LaunchedEffect(Unit){
        viewModel.fetchBreeds()
    }

    Column(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ){
            Text(text = if (isShowingFavourites) "showing favorites" else "showing all breeds",style = MaterialTheme.typography.h6, fontSize = 18.sp)
            Button(onClick = {viewModel.toggleFilter()}) {
                Text(text = if(isShowingFavourites) "Show All" else "Show Favorites")
            }
        }

        println("isShowingFavourites $isShowingFavourites")
        if (isShowingFavourites) {
            FavoriteCarousel(viewModel)
        } else {
            DogBreedList(viewModel)
        }
    }
    
}