package br.ufpe.cin.if710.parkingapp.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import br.ufpe.cin.if710.parkingapp.Utils
import br.ufpe.cin.if710.parkingapp.receiver.LocationBroadcastReceiver
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
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
        private const val UPDATE_INTERVAL: Long = 10 * 1000
        private const val FASTEST_UPDATE_INTERVAL: Long = UPDATE_INTERVAL / 2
        private const val MAX_WAIT_TIME: Long = UPDATE_INTERVAL * 3
    }

    private var mLocationRequest: LocationRequest? = null

    private val locationPendingIntent by lazy {
        val intent = Intent(applicationContext, LocationBroadcastReceiver::class.java)
        intent.action = LocationBroadcastReceiver.ACTION_PROCESS_UPDATES
        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()
        createLocationRequest()

        val client = LocationServices.getFusedLocationProviderClient(this)
        client.requestLocationUpdates(mLocationRequest, locationPendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(Utils.getUniqueId(), Utils.makeNotification(applicationContext, "Testando aqui"))
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest().apply {
            setInterval(UPDATE_INTERVAL)
            setFastestInterval(FASTEST_UPDATE_INTERVAL)
            setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            setMaxWaitTime(MAX_WAIT_TIME)
        }
    }
}