package com.example.dogapi.model.data

data class DogApiResponse(
    val message: Map<String, List<String>>,
    val status: String
)
