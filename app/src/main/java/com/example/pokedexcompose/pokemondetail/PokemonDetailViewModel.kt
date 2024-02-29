package com.example.pokedexcompose.pokemondetail

import androidx.lifecycle.ViewModel
import com.example.pokedexcompose.repository.PokemonRepository
import com.example.pokedexcompose.util.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {
    suspend fun getPokemonInfo(pokemon: String): Resource<Pokemon> {
        return repository.getPokemonInfo(name = pokemon)
    }

}