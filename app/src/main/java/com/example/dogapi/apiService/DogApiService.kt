package com.example.dogapi.apiService

import com.example.dogapi.model.data.DogApiResponse
import com.example.dogapi.model.data.DogImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {

    @GET("/api/breeds/list/all")
    suspend fun getDogBreads(): DogApiResponse

    @GET("/api/breed/{breed}/images/random")
    suspend fun getRandomImagesForBreed(
        @Path("breed") breed: String
    ): DogImageResponse

    @GET("/api/breed/{breed}/{subBreed}/images/random")
    suspend fun getRandomImagesForSubBreed(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String
    ): DogImageResponse
}