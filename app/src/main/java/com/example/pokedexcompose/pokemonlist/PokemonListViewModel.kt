package com.example.pokedexcompose.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedexcompose.data.models.PokedexListEntry
import com.example.pokedexcompose.repository.PokemonRepository
import com.example.pokedexcompose.util.Constants.PAGE_SIZE
import com.example.pokedexcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {
    // pagina inicial
    private var curPage = 0

    // lista de pokemon
    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())

    // mensagem de erro
    var loadError = mutableStateOf("")

    // loading
    var isLoading = mutableStateOf(false)

    // chegou no final
    var endReached = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var isSearchingStarting = true
    var isSearching = mutableStateOf(false)
    fun searchPokemonList(query: String) {
        val listSearch = if (isSearchingStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchingStarting = true
                return@launch
            }
            val result = listSearch.filter { pokemon ->
                pokemon.pokemonName.contains(
                    query.trim(),
                    ignoreCase = true
                ) || pokemon.number.toString() == query.trim()

            }
            if(isSearchingStarting){
                cachedPokemonList=pokemonList.value
                isSearchingStarting=false
            }
            pokemonList.value=result
            isSearching.value=true
        }
    }

    init {
        loadPokemonPaginated()
    }


    fun loadPokemonPaginated() {
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)
            when (result) {
                is Resource.Success -> {
                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count
                    // para ter acesso as imagens que estao dentro da url
                    // precisamos pegar apenas os numeros do final da url
                    // a url termina assim /10/ com essa funcao fica apenas o 10
                    val pokedexEntry = result.data.results.mapIndexed { index, entry ->
                        // se minha url termina com /
                        val number = if (entry.url.endsWith("/")) {
                            // remove o ultimo caracter que é a / so pega enquanto for numero
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            // se o numero n tiver o sufix entao ja pega os numeros
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        // pegando o numero do pokemon que vem da api e trocando
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                        // insere na classe o valorque veio da api
                    }
                    // apos carregar ja atualiza a pagina
                    curPage++
                    loadError.value = ""
                    isLoading.value = false
                    // por final passa os valores para a lista de pokemon
                    // testar dps sem o +
                    pokemonList.value += pokedexEntry

                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false

                }


            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))

            }
        }
    }

}