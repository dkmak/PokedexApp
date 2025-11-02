package com.example.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Pokemon(
    var page: Int = 0,
    @SerialName("name")
    val nameField: String,
    @SerialName("url")
    val url: String
) : Parcelable { // figure out how this fits in, and why this needs a getter

    val name: String
        get() = nameField.replaceFirstChar { it.uppercase() }

    val pokedexIndex: String
        get() = url.split("/".toRegex()).dropLast(1).last()

    val spriteUrl: String
        inline get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/${pokedexIndex}.png"

    val animatedUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +
                "versions/generation-v/black-white/animated/${pokedexIndex}.gif"
}