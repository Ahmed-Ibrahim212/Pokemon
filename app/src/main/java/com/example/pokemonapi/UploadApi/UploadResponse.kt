package com.example.pokemonapi.UploadApi

data class UploadResponse(
    val error: Boolean,
    val message: String,
    val image: String?
)
