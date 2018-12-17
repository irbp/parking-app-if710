package br.ufpe.cin.if710.parkingapp.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.ufpe.cin.if710.parkingapp.Utils

class NotificationBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_ACCEPT_PARKING =
            "br.ufpe.cin.if710.parkingapp.receiver.NotificationBroadcastReceiver.ACCEPT_PARKING"
        const val ACTION_REJECT_PARKING =
            "br.ufpe.cin.if710.parkingapp.receiver.NotificationBroadcastReceiver.REJECT_PARKING"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = intent?.getIntExtra(Utils.EXTRA_GEOFENCE_NOTIFICATION_ID, 0)
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId!!)
        if (intent.action == (ACTION_ACCEPT_PARKING)) {
            // TODO inserir na lista de estacionadas e ir pra activity com a lista
        }
    }
}