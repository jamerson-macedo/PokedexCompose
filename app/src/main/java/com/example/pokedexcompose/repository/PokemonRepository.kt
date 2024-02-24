package com.example.pokedexcompose.repository

import com.example.pokedexcompose.data.remote.PokeApi
import com.example.pokedexcompose.util.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi) {
    suspend fun getPokemonList(limit:Int,offset:Int):Resource<PokemonList>{
        val response=try {
            api.getPokemonList(limit,offset)

        }catch (e:Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(name:String):Resource<Pokemon>{
        val response=try {
            api.getPokemonInfo(name)

        }catch (e:Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }
}