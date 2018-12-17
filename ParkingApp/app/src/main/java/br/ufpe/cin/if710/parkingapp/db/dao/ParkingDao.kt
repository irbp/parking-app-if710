package br.ufpe.cin.if710.parkingapp.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import br.ufpe.cin.if710.parkingapp.db.entity.Parking

@Dao
interface ParkingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(parking: Parking)

    @Query("SELECT * FROM parking_table WHERE id = :id")
    fun getParking(id: String): Parking

    @Query("SELECT * FROM parking_table")
    fun getAllParkings(): List<Parking>

}