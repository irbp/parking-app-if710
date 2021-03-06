package br.ufpe.cin.if710.parkingapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import br.ufpe.cin.if710.parkingapp.ParkingApp
import br.ufpe.cin.if710.parkingapp.Utils
import br.ufpe.cin.if710.parkingapp.db.entity.Parking
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class LocationBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_PROCESS_UPDATES =
            "br.ufpe.cin.if710.parkingapp.receiver.LocationBroadcastService.PROCESS_UPDATES"
        const val ACTION_GEOFENCE_TRANSITIONS =
            "br.ufpe.cin.if710.parkingapp.receiver.LocationBroadcastService.GEOFENCE_TRANSITIONS"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(ACTION_PROCESS_UPDATES)) {
            Log.d("LocationFgService", "Recebi uma parada aqui")
        } else if (intent?.action.equals(ACTION_GEOFENCE_TRANSITIONS)) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            if (geofencingEvent.hasError()) {
                Log.e("LocationFgService", geofencingEvent.errorCode.toString())
                return
            }
            handleEvent(geofencingEvent, context!!)
        }
    }

    private fun handleEvent(event: GeofencingEvent, context: Context) {
        if (event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
            val parking = getFirstParking(event.triggeringGeofences)
            val message = "Você estacionou no " + parking?.name + "?"
            Utils.showGeofenceNotification(context, message, parking!!)
        }
    }

    private fun getFirstParking(triggeringGeofences: List<Geofence>): Parking? {
        val firstGeofence = triggeringGeofences[0]
        val dataRepository = ParkingApp().getDataRepository()
        return dataRepository.getParking(firstGeofence.requestId)
    }
}