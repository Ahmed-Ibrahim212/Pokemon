package com.example.pokemonapi


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

    private var myInstance: Retrofit? = null
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

   private val retofit = Retrofit.Builder()
       .baseUrl("https://pokeapi.co/api/v2/")
       .addConverterFactory(MoshiConverterFactory.create(moshi))
       .build()

    object RetrofitClient{
        val retrofitService: PokemonInterface by lazy {
            retofit.create(PokemonInterface::class.java)
        }
    }


