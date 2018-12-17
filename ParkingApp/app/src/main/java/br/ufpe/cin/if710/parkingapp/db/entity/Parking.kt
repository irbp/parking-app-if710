package br.ufpe.cin.if710.parkingapp.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "parking_table")
data class Parking (
    @PrimaryKey() var id: String,
    var name: String,
    @ColumnInfo(name = "free_minutes") var freeMinutes: Int = 0,
    @ColumnInfo(name = "hour_price") var hourPrice: Float,
    @ColumnInfo(name = "opening_time") var openingTime: Date,
    @ColumnInfo(name = "closing_time") var closingTime: Date,
    var latitude: Double,
    var longitude: Double,
    var radius: Float
) : Serializable
