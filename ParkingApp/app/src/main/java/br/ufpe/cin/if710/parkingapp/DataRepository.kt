package br.ufpe.cin.if710.parkingapp

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.LiveData
import br.ufpe.cin.if710.parkingapp.db.ParkingAppDatabase
import br.ufpe.cin.if710.parkingapp.db.dao.ParkingDao
import br.ufpe.cin.if710.parkingapp.db.dao.ParkingDetailsDao
import br.ufpe.cin.if710.parkingapp.db.dao.UserDao
import br.ufpe.cin.if710.parkingapp.db.entity.Parking
import br.ufpe.cin.if710.parkingapp.db.entity.ParkingDetails
import br.ufpe.cin.if710.parkingapp.db.entity.User
import br.ufpe.cin.if710.parkingapp.network.api.ApiService
import br.ufpe.cin.if710.parkingapp.network.api.Session
import br.ufpe.cin.if710.parkingapp.network.api.model.ParkingResponse
import br.ufpe.cin.if710.parkingapp.utils.inject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataRepository(application: Application) {

    private val parkingDetailsDao: ParkingDetailsDao
    private val parkingDao: ParkingDao
    private val userDao: UserDao
    private val apiService by inject<ApiService>()
    private val session by inject<Session>()

    init {
        val parkingAppDatabase = ParkingAppDatabase.getInstance(application)
        parkingDetailsDao = parkingAppDatabase.parkingDetailsDao()
        parkingDao = parkingAppDatabase.parkingDao()
        userDao = parkingAppDatabase.userDao()
    }

    // ParkingResponse details operations
    fun getAllParkingDetails(): LiveData<List<ParkingDetails>> {
        return parkingDetailsDao.getAll()
    }

    @SuppressLint("CheckResult")
    fun updateParkingsFromApi() {
        val userSession = session.getCurrentUserSession()!!

        apiService
            .getUserHistory(userSession.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                insertParkingDetails(
                    ParkingDetails(
                        it.id,
                        it.amountSpent,
                        it.checkIn,
                        it.checkOut,
                        it.parkingId,
                        userSession.id
                    )
                )
            }
    }

    fun getParkingsByUser(userId: String): LiveData<List<ParkingDetails>> {
        return parkingDetailsDao.getByUser(userId)
    }

    fun insertParkingDetails(parkingDetails: ParkingDetails) {
        parkingDetailsDao.insert(parkingDetails)
    }

    fun deleteParkingDetails(id: String) {
        parkingDetailsDao.deleteEntry(id)
    }

    fun deleteUserParkingDetails(userId: String) {
        parkingDetailsDao.deleteByUser(userId)
    }

    fun deleteAllParkingDetails() {
        parkingDetailsDao.deleteAll()
    }

    // User operations
    fun getUser(id: String): User {
        return userDao.getUser(id)
    }

    fun insertUser(user: User) {
        userDao.insert(user)
    }

    fun deleteUser(id: String) {
        userDao.deleteEntry(id)
    }

    // ParkingResponse
    fun getParking(id: String): Parking {
        return parkingDao.getParking(id)
    }

    /**
     * TODO: Request all parkings around
     */
    fun getAllParkingsFromNetwork(): Observable<Array<ParkingResponse>> {
        return apiService.
            getAllParkingsAround(8, 8, 1000)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllParkings(): List<Parking> {
        return parkingDao.getAllParkings()
    }
}