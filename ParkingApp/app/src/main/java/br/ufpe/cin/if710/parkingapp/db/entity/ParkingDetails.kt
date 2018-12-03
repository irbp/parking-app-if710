package br.ufpe.cin.if710.parkingapp.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "parking_details")
data class ParkingDetails(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "amount_spent") var amountSpent: Float,
    @ColumnInfo(name = "check_in") var checkIn: Date,
    @ColumnInfo(name = "check_out") var checkOut: Date,
    @ColumnInfo(name = "parking_id") var parkingId: Int,
    @ColumnInfo(name = "user_id") var userId: Int
)