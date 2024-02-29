package com.example.pokedexcompose.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import com.example.pokedexcompose.ui.theme.AtkColor
import com.example.pokedexcompose.ui.theme.DefColor
import com.example.pokedexcompose.ui.theme.HPColor
import com.example.pokedexcompose.ui.theme.SpAtkColor
import com.example.pokedexcompose.ui.theme.SpDefColor
import com.example.pokedexcompose.ui.theme.SpdColor
import com.example.pokedexcompose.ui.theme.TypeBug
import com.example.pokedexcompose.ui.theme.TypeDark
import com.example.pokedexcompose.ui.theme.TypeDragon
import com.example.pokedexcompose.ui.theme.TypeElectric
import com.example.pokedexcompose.ui.theme.TypeFairy
import com.example.pokedexcompose.ui.theme.TypeFighting
import com.example.pokedexcompose.ui.theme.TypeFire
import com.example.pokedexcompose.ui.theme.TypeFlying
import com.example.pokedexcompose.ui.theme.TypeGhost
import com.example.pokedexcompose.ui.theme.TypeGrass
import com.example.pokedexcompose.ui.theme.TypeGround
import com.example.pokedexcompose.ui.theme.TypeIce
import com.example.pokedexcompose.ui.theme.TypeNormal
import com.example.pokedexcompose.ui.theme.TypePoison
import com.example.pokedexcompose.ui.theme.TypePsychic
import com.example.pokedexcompose.ui.theme.TypeRock
import com.example.pokedexcompose.ui.theme.TypeSteel
import com.example.pokedexcompose.ui.theme.TypeWater
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Stat
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Type
import java.util.Locale

fun parseTypeToColor(type: Type): Color {
    return when (type.type.name.toLowerCase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Black
    }
}

fun parseStatToColor(stat: Stat): Color {
    return when (stat.stat.name.toLowerCase()) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when (stat.stat.name.toLowerCase()) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}