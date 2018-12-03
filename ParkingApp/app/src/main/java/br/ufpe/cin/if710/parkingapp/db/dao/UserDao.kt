package br.ufpe.cin.if710.parkingapp.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import br.ufpe.cin.if710.parkingapp.db.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM user_table WHERE id = :id")
    fun deleteEntry(id: Int)

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun getUser(id: Int): User

}