package br.ufpe.cin.if710.parkingapp

import android.app.Application
import br.ufpe.cin.if710.parkingapp.network.api.FirestoreApiService
import br.ufpe.cin.if710.parkingapp.utils.provideSingleton

object DIModule {
    fun initialize(application: Application) {
        provideSingleton { application }
        provideSingleton { FirestoreApiService }
    }
}