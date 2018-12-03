package br.ufpe.cin.if710.parkingapp.model.repo

import com.google.firebase.firestore.FirebaseFirestore

class ParkingRepository {
    private val firestore by inject<FirebaseFirestore>()
}