package com.example.dogapi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DogImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreedImage(dogImageEntity: DogImageEntity)

    @Query("SELECT * FROM dog_image WHERE id = :id")
    suspend fun getBreedImageById(id: String): DogImageEntity?
}