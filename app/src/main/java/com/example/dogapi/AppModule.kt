package com.example.dogapi

import android.content.Context
import androidx.room.Room
import com.example.dogapi.apiService.DogApiService
import com.example.dogapi.model.repository.DogRepository
import com.example.dogapi.room.DogBreedDao
import com.example.dogapi.room.DogImageDao
import com.example.dogapi.room.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideDogApiService(retrofit: Retrofit) : DogApiService{
        return retrofit.create(DogApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) :AppDataBase{
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideFavoriteDao(database: AppDataBase) : FavoriteDao{
        return database.favoriteDao()
    }

    @Provides
    fun provideDogBreedDao(database: AppDataBase) : DogBreedDao{
        return database.dogBreedDao()
    }

    @Provides
    fun provideDogImageDao(database: AppDataBase) : DogImageDao {
        return database.dogImageDao()
    }

    @Provides
    @Singleton
    fun provideDogRepository(apiService: DogApiService,favoriteDao: FavoriteDao,dogBreedDao: DogBreedDao,dogImageDao: DogImageDao) : DogRepository{
        return DogRepository(apiService,favoriteDao,dogBreedDao,dogImageDao)
    }
}

