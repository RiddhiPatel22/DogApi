package com.example.dogapi.model.repository

import com.example.dogapi.apiService.DogApiService
import com.example.dogapi.model.data.DogBreed
import com.example.dogapi.room.FavoriteDao
import com.example.dogapi.room.FavoriteEntity
import kotlinx.coroutines.flow.Flow


class DogRepository(private val apiService: DogApiService, private val favoriteDao: FavoriteDao) {

    suspend fun fetchDogBreeds() : List<DogBreed>{
        val response = apiService.getDogBreads()
        return response.message.map { DogBreed(it.key,it.value) }
    }

    fun getFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()

    suspend fun getRandomBreedImage(breed: String): String{
        return apiService.getRandomImagesForBreed(breed).message
    }

    suspend fun getRandomSubBreedImage(breed: String,subBreed: String): String{
        return apiService.getRandomImagesForSubBreed(breed,subBreed).message
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