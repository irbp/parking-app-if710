package br.ufpe.cin.if710.parkingapp.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey() var id: String,
    var name: String,
    var email: String
)