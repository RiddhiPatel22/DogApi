package com.example.dogapi.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class DogBreedEntity(
    @PrimaryKey val breedName: String,
    val subBreed : List<String>
)
