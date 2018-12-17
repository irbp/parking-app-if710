package br.ufpe.cin.if710.parkingapp

import android.Manifest
import android.app.Application
import android.content.Intent
import android.os.Build
import br.ufpe.cin.if710.parkingapp.service.LocationForegroundService

class ParkingApp : Application() {

    fun getDataRepository() = DataRepository(this)

    override fun onCreate() {
        super.onCreate()

        DataRepository(this)
        DIModule.initialize(this)

//        if (Utils.checkPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            Intent(this, LocationForegroundService::class.java).also { service ->
//                this.let {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        it.startForegroundService(service)
//                    } else {
//                        it.startService(service)
//                    }
//                }
//            }
//        }
    }
}