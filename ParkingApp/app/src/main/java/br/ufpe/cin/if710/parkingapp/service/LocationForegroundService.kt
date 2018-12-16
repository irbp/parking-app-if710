package br.ufpe.cin.if710.parkingapp.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.util.Log
import br.ufpe.cin.if710.parkingapp.Utils
import br.ufpe.cin.if710.parkingapp.receiver.LocationBroadcastReceiver
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class LocationForegroundService : Service(), GoogleApiClient.OnConnectionFailedListener,
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
    }

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()

        val fusedClient = LocationServices.getFusedLocationProviderClient(this)
        val mLocationRequest = createLocationRequest()
        fusedClient.requestLocationUpdates(mLocationRequest, locationPendingIntent)
        val geofencingClient = LocationServices.getGeofencingClient(this)
        val geofence = buildGeofence()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            geofencingClient
                .addGeofences(buildGeofenceRequest(geofence), geofencePendingIntent)
                .addOnSuccessListener {
                    Log.d(TAG, "Geofence adicionado com sucesso")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Falha ao adicionar o geofence")
                }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(Utils.getUniqueId(), Utils.makeNotification(applicationContext, "Testando aqui"))
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

    private fun buildGeofenceRequest(geofence: Geofence?): GeofencingRequest? {
        return GeofencingRequest.Builder()
            .setInitialTrigger(0)
            .addGeofences(listOf(geofence))
            .build()
    }

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun buildGeofence(): Geofence? {
        val latitude = -7.94625
        val longitude = -34.9133

        return Geofence.Builder()
            .setRequestId("1001")
            .setCircularRegion(latitude, longitude, 600.toFloat())
            .setLoiteringDelay(60 * 1000)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }

    private fun createLocationRequest(): LocationRequest {
       return LocationRequest().apply {
            setInterval(UPDATE_INTERVAL)
            setFastestInterval(FASTEST_UPDATE_INTERVAL)
            setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            setMaxWaitTime(MAX_WAIT_TIME)
        }
    }
}