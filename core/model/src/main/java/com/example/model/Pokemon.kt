package com.example.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
// import androidx.compose.runtime.Immutable

// @Immutable (optimization)
@Parcelize
@Serializable
data class Pokemon(
  var page: Int = 0,
  @SerialName(value = "name")
  val nameField: String,
  @SerialName(value = "url") val url: String,
) : Parcelable {

  val name: String
    get() = nameField.replaceFirstChar { it.uppercase() }

  val imageUrl: String
    inline get() {
      val index = url.split("/".toRegex()).dropLast(1).last()
      return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
        "pokemon/other/official-artwork/$index.png"
    }
}
