package br.ufpe.cin.if710.parkingapp

import android.app.Application
import android.arch.lifecycle.LiveData
import br.ufpe.cin.if710.parkingapp.db.ParkingAppDatabase
import br.ufpe.cin.if710.parkingapp.db.dao.ParkingDetailsDao
import br.ufpe.cin.if710.parkingapp.db.dao.UserDao
import br.ufpe.cin.if710.parkingapp.db.entity.ParkingDetails
import br.ufpe.cin.if710.parkingapp.db.entity.User

class DataRepository(application: Application) {

    private val parkingDetailsDao: ParkingDetailsDao
    private val userDao: UserDao

    init {
        val parkingAppDatabase = ParkingAppDatabase.getInstance(application)
        parkingDetailsDao = parkingAppDatabase.parkingDetailsDao()
        userDao = parkingAppDatabase.userDao()
    }

    // Parking details operations
    fun getAllParkingDetails(): LiveData<List<ParkingDetails>> {
        return parkingDetailsDao.getAll()
    }

    fun getParkingsByUser(userId: Int): LiveData<List<ParkingDetails>> {
        return parkingDetailsDao.getByUser(userId)
    }

    fun insertParkingDetails(parkingDetails: ParkingDetails) {
        parkingDetailsDao.insert(parkingDetails)
    }

    fun deleteParkingDetails(id: Int) {
        parkingDetailsDao.deleteEntry(id)
    }

    fun deleteUserParkingDetails(userId: Int) {
        parkingDetailsDao.deleteByUser(userId)
    }

    fun deleteAllParkingDetails() {
        parkingDetailsDao.deleteAll()
    }

    // User operations
    fun getUser(id: Int): User {
        return userDao.getUser(id)
    }

    fun insertUser(user: User) {
        userDao.insert(user)
    }

    fun deleteUser(id: Int) {
        userDao.deleteEntry(id)
    }
}