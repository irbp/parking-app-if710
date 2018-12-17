package br.ufpe.cin.if710.parkingapp

import android.app.Application
import android.util.Log

class ParkingApp : Application() {

    fun getDataRepository() = DataRepository(this)

    override fun onCreate() {
        super.onCreate()

        DataRepository(this)
        DIModule.initialize(this)
    }
}