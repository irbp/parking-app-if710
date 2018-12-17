package br.ufpe.cin.if710.parkingapp.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.arch.lifecycle.LifecycleService
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.util.Log
import br.ufpe.cin.if710.parkingapp.DataRepository
import br.ufpe.cin.if710.parkingapp.ParkingApp
import br.ufpe.cin.if710.parkingapp.Utils
import br.ufpe.cin.if710.parkingapp.db.entity.Parking
import br.ufpe.cin.if710.parkingapp.receiver.LocationBroadcastReceiver
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class LocationForegroundService : LifecycleService(), GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {
    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d(TAG, "onConnectionFailed")
    }

    override fun onConnected(p0: Bundle?) {
        Log.d(TAG, "onConnected")
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d(TAG, "onConnectionSuspended")
    }

    companion object {
        const val TAG = "LocationFgService"
        private const val UPDATE_INTERVAL: Long = 10 * 1000 // 10 segundos
        private const val FASTEST_UPDATE_INTERVAL: Long = UPDATE_INTERVAL / 2 // 5 segundos
        private const val MAX_WAIT_TIME: Long = UPDATE_INTERVAL * 3 // 30 segundos
        private const val LOITERING_DELAY = 1 * 60 * 1000 // 1 minuto
    }

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()

        val fusedClient = LocationServices.getFusedLocationProviderClient(this)
        val mLocationRequest = createLocationRequest()
        fusedClient.requestLocationUpdates(mLocationRequest, locationPendingIntent)
        val geofencingClient = LocationServices.getGeofencingClient(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            val parkingGeofences = getParkingGeofences()
            geofencingClient
                .addGeofences(buildGeofenceRequest(parkingGeofences), geofencePendingIntent)
                .addOnSuccessListener {
                    Log.d(TAG, "Geofences adicionados com sucesso")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Falha ao adicionar os geofences")
                }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = Utils.makeNotification(
                this,
                "ParkingApp executando em segundo plano",
                Utils.BOOT_NOTIFICATION_CHANNEL_ID,
                NotificationManager.IMPORTANCE_LOW,
                "ParkingApp Service"
            )
//            startForeground(Utils.getUniqueId(), notification)
        }
    }

    private val locationPendingIntent by lazy {
        val intent = Intent(this, LocationBroadcastReceiver::class.java)
        intent.action = LocationBroadcastReceiver.ACTION_PROCESS_UPDATES
        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private val geofencePendingIntent by lazy {
        val intent = Intent(this, LocationBroadcastReceiver::class.java)
        intent.action = LocationBroadcastReceiver.ACTION_GEOFENCE_TRANSITIONS
        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun buildGeofenceRequest(geofences: List<Geofence>): GeofencingRequest? {
        return GeofencingRequest.Builder()
            .setInitialTrigger(0)
            .addGeofences(geofences)
            .build()
    }

    private fun getParkingGeofences(): List<Geofence> {
        val dataRepository = DataRepository(application)
        val parkings = dataRepository.getAllParkings()
        val parkingGeofences = mutableListOf<Geofence>()
        for (parking in parkings) parkingGeofences.add(buildGeofence(parking))

        return parkingGeofences
    }

    private fun buildGeofence(parking: Parking): Geofence {
        val latitude = parking.latitude
        val longitude = parking.longitude
        val radius = parking.radius
        val requesId = parking.id.toString()

        Log.d(LocationForegroundService.TAG,  parking.name)

        return Geofence.Builder()
            .setRequestId(requesId)
            .setCircularRegion(latitude, longitude, radius)
            .setLoiteringDelay(LOITERING_DELAY)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }

    private fun createLocationRequest(): LocationRequest {
       return LocationRequest().apply {
           interval = UPDATE_INTERVAL
           fastestInterval = FASTEST_UPDATE_INTERVAL
           priority = LocationRequest.PRIORITY_HIGH_ACCURACY
           maxWaitTime = MAX_WAIT_TIME
        }
    }
}