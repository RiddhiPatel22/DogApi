package com.example.dogapi.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapi.model.data.DogBreed
import com.example.dogapi.model.repository.DogRepository
import com.example.dogapi.room.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(private val repository: DogRepository) : ViewModel() {

    private val _dogBreeds = MutableStateFlow<List<DogBreed>>(emptyList())
    val dogBreeds: StateFlow<List<DogBreed>> = _dogBreeds

    private val _randomImages = MutableStateFlow<Map<String, String>>(emptyMap())
    val randomImages: StateFlow<Map<String, String>> = _randomImages

    val favorite = repository.getFavorites()

    private val _isShowingFavorites = MutableStateFlow(false)
    val isShowingFavorites: StateFlow<Boolean> = _isShowingFavorites

    val filteredBreeds = combine(_dogBreeds, favorite, _isShowingFavorites) { breeds, favs, showFavs ->
        if (showFavs) {
            breeds.mapNotNull { breed ->
                // Check if the main breed is favorite
                val isMainBreedFavorite = favs.any { it.id == breed.breed }

                // Filter the sub-breeds that are favorite
                val favoriteSubBreeds = breed.subBreeds.filter { subBreed ->
                    favs.any { it.id == "${breed.breed}_$subBreed" }
                }

                when {
                    isMainBreedFavorite -> breed // Show entire breed with all sub-breeds
                    favoriteSubBreeds.isNotEmpty() -> DogBreed(
                        breed = breed.breed,
                        subBreeds = favoriteSubBreeds
                    ) // Show breed with only favorite sub-breeds
                    else -> null // Exclude breed if neither main breed nor sub-breeds are favorite
                }
            }
        } else {
            breeds // Show all breeds if not filtering by favorites
        }
    }

    fun fetchBreeds() {
        viewModelScope.launch {
            try {
                val breeds = repository.fetchDogBreeds()
                _dogBreeds.value = breeds

                // Fetch random images for all main breeds
                breeds.forEach { breed ->
                    fetchRandomImage(breed.breed)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchRandomImage(breed: String, subBreed: String? = null) {

        val imageKey = "${breed}_${subBreed ?: ""}"
        if (_randomImages.value.containsKey(imageKey)) return

        viewModelScope.launch {
            val image = if (subBreed == null) {
                repository.getRandomBreedImage(breed)
            } else {
                repository.getRandomSubBreedImage(breed, subBreed)
            }
            _randomImages.value += ("${breed}_${subBreed ?: ""}" to image)
        }
    }

    fun toggleFavorite(id: String, name: String, isSubBreed: Boolean) {
        viewModelScope.launch {
            if (repository.isFavorite(id)) {
                repository.removeFavorite(id)
            } else {
                repository.addFavorite(FavoriteEntity(id, name, isSubBreed))
            }

            // Fetch a new random image for the breed or sub-breed
            if (isSubBreed) {
                val parts = id.split("_")
                if (parts.size == 2) {
                    fetchRandomImage(parts[0], parts[1])
                }
            } else {
                fetchRandomImage(id)
            }
        }
    }

    fun toggleFilter() {
        _isShowingFavorites.value = !_isShowingFavorites.value
    }

//    fun fetchRandomImagesForFavorites() {
//        viewModelScope.launch {
//            favorite.collect { favList ->
//                favList.forEach { favorite ->
//                    if (favorite.isSubBread) {
//                        val breedAndSub = favorite.id.split("_")
//                        if (breedAndSub.size == 2) {
//                            fetchRandomImage(breedAndSub[0], breedAndSub[1])
//                        }
//                    } else {
//                        fetchRandomImage(favorite.id)
//                    }
//                }
//            }
//        }
//    }

}