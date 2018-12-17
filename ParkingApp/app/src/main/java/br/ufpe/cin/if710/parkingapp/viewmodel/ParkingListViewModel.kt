package br.ufpe.cin.if710.parkingapp.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import br.ufpe.cin.if710.parkingapp.ParkingApp
import br.ufpe.cin.if710.parkingapp.db.entity.Parking
import br.ufpe.cin.if710.parkingapp.db.entity.ParkingDetails
import br.ufpe.cin.if710.parkingapp.network.api.Session
import br.ufpe.cin.if710.parkingapp.receiver.LocationBroadcastReceiver
import br.ufpe.cin.if710.parkingapp.utils.inject
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices

class ParkingListViewModel(application: Application) : AndroidViewModel(application),
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private val dataRepository = getApplication<ParkingApp>().getDataRepository()
    private val parkingDetailsList = MediatorLiveData<List<ParkingDetails>>()
    private val session by inject<Session>()

    companion object {
        const val TAG = "LocationFgService"
        private const val UPDATE_INTERVAL: Long = 10 * 1000 // 10 segundos
        private const val FASTEST_UPDATE_INTERVAL: Long = UPDATE_INTERVAL / 2 // 5 segundos
        private const val MAX_WAIT_TIME: Long = UPDATE_INTERVAL * 3 // 30 segundos
        private const val LOITERING_DELAY = 1 * 60 * 1000 // 1 minuto
    }

    init {
        getAllParkingDetails()
        startGeofenceService()
    }

    fun getParkingDetailsList(): LiveData<List<ParkingDetails>> = parkingDetailsList

    fun getUserParkings(userId: Int) {
        parkingDetailsList.addSource(dataRepository.getParkingsByUser(userId)) {
            parkingDetailsList.postValue(it)
        }
    }

    fun getAllParkingDetails() {
        parkingDetailsList.addSource(dataRepository.getAllParkingDetails()) {
            parkingDetailsList.postValue(it)
        }
    }

    fun addParkingDetails(parkingDetails: ParkingDetails) {
        AsyncTask.execute { dataRepository.insertParkingDetails(parkingDetails) }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d(TAG, "onConnectionFailed")
    }

    override fun onConnected(p0: Bundle?) {
        Log.d(TAG, "onConnected")
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d(TAG, "onConnectionSuspended")
    }

    @SuppressLint("MissingPermission")
    private fun startGeofenceService() {
        val context = getApplication<ParkingApp>().applicationContext
        val fusedClient = LocationServices.getFusedLocationProviderClient(context)
        val mLocationRequest = createLocationRequest()
        fusedClient.requestLocationUpdates(mLocationRequest, locationPendingIntent(context))
        val geofencingClient = LocationServices.getGeofencingClient(context)
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            val parkingGeofences = getParkingGeofences()
            geofencingClient
                .addGeofences(buildGeofenceRequest(parkingGeofences), geofencePendingIntent(context))
                .addOnSuccessListener {
                    Log.d(TAG, "Geofences adicionados com sucesso")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Falha ao adicionar os geofences")
                }
        }
    }

    private fun geofencePendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, LocationBroadcastReceiver::class.java)
        intent.action = LocationBroadcastReceiver.ACTION_GEOFENCE_TRANSITIONS
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun locationPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, LocationBroadcastReceiver::class.java)
        intent.action = LocationBroadcastReceiver.ACTION_PROCESS_UPDATES
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun buildGeofenceRequest(geofences: List<Geofence>): GeofencingRequest? {
        return GeofencingRequest.Builder()
            .setInitialTrigger(0)
            .addGeofences(geofences)
            .build()
    }

    private fun getParkingGeofences(): List<Geofence> {
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

        Log.d(TAG,  parking.name)

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
            priority = PRIORITY_HIGH_ACCURACY
            maxWaitTime = MAX_WAIT_TIME
        }
    }

}