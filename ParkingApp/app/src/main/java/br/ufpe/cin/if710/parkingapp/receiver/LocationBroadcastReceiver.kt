package br.ufpe.cin.if710.parkingapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import br.ufpe.cin.if710.parkingapp.service.LocationForegroundService

class LocationBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_PROCESS_UPDATES = "br.ufpe.cin.if710.parkingapp.receiver.PROCESS_UPDATES"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(ACTION_PROCESS_UPDATES)) {
            Log.d(LocationForegroundService.TAG, "Recebi uma parada aqui")
        }
    }
}