package br.ufpe.cin.if710.parkingapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import br.ufpe.cin.if710.parkingapp.service.LocationForegroundService

class BootBroadcastReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent(context, LocationForegroundService::class.java).also { service ->
                context?.let {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        it.startForegroundService(service)
                    } else {
                        it.startService(service)
                    }
                }
            }
        }
    }
}