package br.ufpe.cin.if710.parkingapp.model.entity

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class Parking(
    var name: String = "",
    var location: GeoPoint = GeoPoint(0.0, 0.0),
    var openingTime: Timestamp = Timestamp(null),
    var closingTime: Timestamp = Timestamp(null),
    var hourPrice: String = "",
    var freeMinutes: Number = 0
) {
    companion object {
        const val COLLECTION = "parkings"
    }
}