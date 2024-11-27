package com.example.dogapi

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dogapi.room.FavoriteDao
import com.example.dogapi.room.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version =1, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao
}