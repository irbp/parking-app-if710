package br.ufpe.cin.if710.parkingapp.network.api

import com.google.gson.annotations.SerializedName

data class FirestoreInteger(
    @SerializedName("integerValue") val value: Int
)

data class FirestoreString(
    @SerializedName("stringValue") val value: String
)

data class FirestoreTimestamp(
    @SerializedName("timestampValue") val value: String
)

data class GeoPoint(
    @SerializedName("latitude") val latitude: Number,
    @SerializedName("longitude") val longitude: Number
)

data class FirestoreGeoPoint(
    @SerializedName("geoPointValue") val geoPoint: GeoPoint
)

data class Response<A>(
    @SerializedName("name") val name: String,
    @SerializedName("fields") val fields: A,
    @SerializedName("createTime") val createTime: String,
    @SerializedName("updateTime") val updateTime: String
)

data class ListResponse<A>(
    @SerializedName("documents") val documents: List<Response<A>>
)