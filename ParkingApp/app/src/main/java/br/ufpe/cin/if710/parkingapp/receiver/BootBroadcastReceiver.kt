package br.ufpe.cin.if710.parkingapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import br.ufpe.cin.if710.parkingapp.service.LocationForegroundService

class BootBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d(LocationForegroundService.TAG, "Android inicializou")
        }
    }
}