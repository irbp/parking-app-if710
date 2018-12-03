package br.ufpe.cin.if710.parkingapp

import android.app.Application

class ParkingApp : Application() {

    fun getDataRepository() = DataRepository(this)

}