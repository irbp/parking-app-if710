package br.ufpe.cin.if710.parkingapp.network.api.model

import com.google.gson.annotations.SerializedName


data class SignInRequest(
    @SerializedName("email") val username: String,
    @SerializedName("password") val password: String
)

data class SignInResponse(
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String
)
