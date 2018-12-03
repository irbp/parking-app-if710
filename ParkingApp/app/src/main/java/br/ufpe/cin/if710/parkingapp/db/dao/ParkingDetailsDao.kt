package br.ufpe.cin.if710.parkingapp.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import br.ufpe.cin.if710.parkingapp.db.entity.ParkingDetails

@Dao
interface ParkingDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(parkingDetails: ParkingDetails)

    @Query("DELETE FROM parking_details WHERE id = :id")
    fun deleteEntry(id: Int)

    @Query("DELETE FROM parking_details WHERE user_id = :userId")
    fun deleteByUser(userId: Int)

    @Query("DELETE FROM parking_details")
    fun deleteAll()

    @Query("SELECT * FROM parking_details WHERE user_id = :userId ORDER BY check_out DESC")
    fun getByUser(userId: Int): LiveData<List<ParkingDetails>>

    @Query("SELECT * FROM parking_details")
    fun getAll(): LiveData<List<ParkingDetails>>

}