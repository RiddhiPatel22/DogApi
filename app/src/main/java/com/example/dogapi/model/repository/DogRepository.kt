package com.example.dogapi.model.repository

import com.example.dogapi.apiService.DogApiService
import com.example.dogapi.model.data.DogBreed
import com.example.dogapi.room.DogBreedDao
import com.example.dogapi.room.DogBreedEntity
import com.example.dogapi.room.DogImageDao
import com.example.dogapi.room.DogImageEntity
import com.example.dogapi.room.FavoriteDao
import com.example.dogapi.room.FavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class DogRepository(
    private val apiService: DogApiService,
    private val favoriteDao: FavoriteDao,
    private val dogBreedDao: DogBreedDao,
    private val dogImageDao: DogImageDao
) {

    suspend fun fetchDogBreeds() : List<DogBreed>{
        return try{
            val response = apiService.getDogBreads()
            val allBreeds = response.message.map { DogBreed(it.key,it.value) }
            dogBreedDao.insertBreeds(allBreeds.map{DogBreedEntity(it.breed,it.subBreeds)})
            allBreeds
        }catch (e : Exception){
            withContext(Dispatchers.IO) {
                val cachedBreed = dogBreedDao.getAllBreeds()
                cachedBreed.map { DogBreed(it.breedName, it.subBreed) }
            }
        }
    }

    fun getFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()

    suspend fun getRandomBreedImage(breed: String): String{
        return try {
           val imageUrl = apiService.getRandomImagesForBreed(breed).message
            dogImageDao.insertBreedImage(DogImageEntity(breed,imageUrl))
           imageUrl
        }catch (e : Exception){
            withContext(Dispatchers.IO) {
                val cachedImage = dogImageDao.getBreedImageById(breed)
                cachedImage!!.message
            }
        }
    }

    suspend fun getRandomSubBreedImage(breed: String,subBreed: String): String{

        return try {
            val imageUrl = apiService.getRandomImagesForSubBreed(breed,subBreed).message
            dogImageDao.insertBreedImage(DogImageEntity("${breed}_${subBreed}",imageUrl))
            imageUrl
        }catch (e : Exception){
            withContext(Dispatchers.IO) {
                val cachedImage = dogImageDao.getBreedImageById("${breed}_${subBreed}")
                println("cachedImage: $cachedImage")
                if (cachedImage != null) {
                    cachedImage.message
                } else {
                    "No image available"
                }
            }
        }
    }

    suspend fun addFavorite(favorite: FavoriteEntity){
        favoriteDao.insertFavorite(favorite)
    }

    suspend fun removeFavorite(id:String){
        favoriteDao.deleteFavorite(id)
    }

    suspend fun isFavorite(id: String): Boolean{
        return favoriteDao.getFavoriteById(id) != null
    }

}