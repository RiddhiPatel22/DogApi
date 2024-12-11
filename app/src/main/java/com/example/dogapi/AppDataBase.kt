package com.example.dogapi

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dogapi.room.Converter
import com.example.dogapi.room.DogBreedDao
import com.example.dogapi.room.DogBreedEntity
import com.example.dogapi.room.DogImageDao
import com.example.dogapi.room.DogImageEntity
import com.example.dogapi.room.FavoriteDao
import com.example.dogapi.room.FavoriteEntity


@Database(entities = [FavoriteEntity::class,DogBreedEntity::class,DogImageEntity::class], version =1,
    exportSchema = true)
@TypeConverters(Converter::class)
abstract class AppDataBase : RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao
    abstract fun dogBreedDao(): DogBreedDao
    abstract fun dogImageDao(): DogImageDao
}