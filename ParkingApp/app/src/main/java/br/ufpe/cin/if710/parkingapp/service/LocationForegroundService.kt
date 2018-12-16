package br.ufpe.cin.if710.parkingapp.service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import br.ufpe.cin.if710.parkingapp.Utils

class LocationForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(Utils.getUniqueId(), Utils.makeNotification(applicationContext, "Testando aqui"))
        }
    }
    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}