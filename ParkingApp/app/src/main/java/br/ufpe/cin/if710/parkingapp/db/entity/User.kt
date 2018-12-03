package br.ufpe.cin.if710.parkingapp.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    var email: String
)