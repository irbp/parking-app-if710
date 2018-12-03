package br.ufpe.cin.if710.parkingapp.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.os.AsyncTask
import br.ufpe.cin.if710.parkingapp.ParkingApp
import br.ufpe.cin.if710.parkingapp.db.entity.ParkingDetails

class ParkingListViewModel(application: Application) : AndroidViewModel(application) {

    private val dataRepository = getApplication<ParkingApp>().getDataRepository()
    private val parkingDetailsList = MediatorLiveData<List<ParkingDetails>>()

    init {
        getAllParkingDetails()
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

}