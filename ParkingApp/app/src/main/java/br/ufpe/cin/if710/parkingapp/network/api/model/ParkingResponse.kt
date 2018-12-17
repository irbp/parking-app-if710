package br.ufpe.cin.if710.parkingapp.network.api.model

import com.google.gson.annotations.SerializedName

data class GeoPoint(
    @SerializedName("lat") val latitude: Number,
    @SerializedName("lng") val longitude: Number
)

data class ParkingResponse(
    @SerializedName("name") val name: String,
    @SerializedName("hourPrice") val hourPrice: Number,
    @SerializedName("location") val location: GeoPoint,
    @SerializedName("freeMinutes") val freeMinutes: Number,
    @SerializedName("openingTime") val openingTime: String,
    @SerializedName("closingTime") val closingTime: String,
    @SerializedName("radius") val radius: String,
    @SerializedName("distance") val distance: Number
)