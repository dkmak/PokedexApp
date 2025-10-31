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
  val name: String,

  @SerialName("url")
  val url: String
): Parcelable