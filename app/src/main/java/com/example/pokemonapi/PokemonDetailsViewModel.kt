package com.example.pokemonapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class PokemonDetailsViewModel() : ViewModel() {
    private var pokemonIndividualData = MutableLiveData<Pokemon>()
    fun getPokemonIndividualDataDetails(): LiveData<Pokemon> {
        return pokemonIndividualData
    }
    /**
     * Use kotlin coroutine to make network call and get json results of each pokemon
     */
    fun getPokemonIndividualDetails(id:String){
        viewModelScope.launch {
            try {
                val pokemonIndividualResults = RetrofitClient.retrofitService.getPokemonDetails(id)
                pokemonIndividualData.value = pokemonIndividualResults
            } catch (e: Exception){
                Log.d("FAILUREDETAILS", "$e")
            }
        }
    }
}