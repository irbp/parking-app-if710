package br.ufpe.cin.if710.parkingapp.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.ufpe.cin.if710.parkingapp.DataRepository
import br.ufpe.cin.if710.parkingapp.Utils
import br.ufpe.cin.if710.parkingapp.db.entity.Parking
import br.ufpe.cin.if710.parkingapp.db.entity.ParkingDetails
import br.ufpe.cin.if710.parkingapp.network.api.Session
import br.ufpe.cin.if710.parkingapp.ui.ParkingListActivity
import br.ufpe.cin.if710.parkingapp.utils.inject
import java.text.SimpleDateFormat
import java.util.*
import android.support.v4.content.ContextCompat.startActivity



val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")

class NotificationBroadcastReceiver : BroadcastReceiver() {

    val dataRepository by inject<DataRepository>()
    val session by inject<Session>()

    companion object {
        const val ACTION_ACCEPT_PARKING =
            "br.ufpe.cin.if710.parkingapp.receiver.NotificationBroadcastReceiver.ACCEPT_PARKING"
        const val ACTION_REJECT_PARKING =
            "br.ufpe.cin.if710.parkingapp.receiver.NotificationBroadcastReceiver.REJECT_PARKING"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationId = intent?.getIntExtra(Utils.EXTRA_GEOFENCE_NOTIFICATION_ID, 0)
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val parking = intent?.getSerializableExtra(Utils.EXTRA_PARKING) as Parking
        val checkIn = intent.getSerializableExtra(Utils.EXTRA_CHECKIN) as Date

        notificationManager.cancel(notificationId!!)
        if (intent.action == ACTION_ACCEPT_PARKING) {
            dataRepository.insertParkingDetails(
                ParkingDetails(
                    sdf.format(checkIn),
                    parking.hourPrice,
                    checkIn,
                    checkIn,
                    parking.id,
                    session.getCurrentUserSession()!!.id
                )
            )
            val i = Intent(context.applicationContext, ParkingListActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }
}