package com.example.dogapi.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog_image")
data class DogImageEntity (
    @PrimaryKey val id: String, // Combination of breed and subBreed
             val message: String
)