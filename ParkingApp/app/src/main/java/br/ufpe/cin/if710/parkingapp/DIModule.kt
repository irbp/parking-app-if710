package br.ufpe.cin.if710.parkingapp

import android.app.Application
import br.ufpe.cin.if710.parkingapp.network.api.ApiService
import br.ufpe.cin.if710.parkingapp.network.api.Session
import br.ufpe.cin.if710.parkingapp.utils.provideSingleton

object DIModule {
    fun initialize(application: Application) {
        provideSingleton { application }
        provideSingleton { ApiService.create() }
        provideSingleton { Session() }
        provideSingleton { DataRepository(application) }

    }
}