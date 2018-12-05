package br.ufpe.cin.if710.parkingapp.network.model

import br.ufpe.cin.if710.parkingapp.network.api.*
import com.google.gson.annotations.SerializedName


data class Parking(
    @SerializedName("name") val name: FirestoreString,
    @SerializedName("hourPrice") val hourPrice: FirestoreInteger,
    @SerializedName("location") val location: FirestoreGeoPoint,
    @SerializedName("freeMinutes") val freeMinutes: FirestoreInteger,
    @SerializedName("openingTime") val openingTime: FirestoreTimestamp,
    @SerializedName("closingTime") val closingTime: FirestoreTimestamp
)

typealias ParkingResponse = Response<Parking>
typealias ParkingListResponse = ListResponse<Parking>