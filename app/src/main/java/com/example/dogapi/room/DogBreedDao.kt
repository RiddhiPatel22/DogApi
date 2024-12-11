package com.example.dogapi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DogBreedDao {
    @Query("SELECT * FROM breeds")
    fun getAllBreeds(): List<DogBreedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreeds(breeds: List<DogBreedEntity>)
}