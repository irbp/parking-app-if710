package br.ufpe.cin.if710.parkingapp.network.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class HistoryResponse(
    @SerializedName("_id") var id: String,
    @SerializedName("name") val name: String,
    @SerializedName("amountSpent") var amountSpent: Float,
    @SerializedName("checkIn") var checkIn: Date,
    @SerializedName("checkOut") var checkOut: Date,
    @SerializedName("parkingId") var parkingId: String
)