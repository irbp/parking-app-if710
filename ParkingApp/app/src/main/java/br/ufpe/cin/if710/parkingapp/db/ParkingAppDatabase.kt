package br.ufpe.cin.if710.parkingapp.db

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.*
import android.os.AsyncTask
import br.ufpe.cin.if710.parkingapp.db.converter.DateConverter
import br.ufpe.cin.if710.parkingapp.db.dao.ParkingDetailsDao
import br.ufpe.cin.if710.parkingapp.db.dao.UserDao
import br.ufpe.cin.if710.parkingapp.db.entity.Parking
import br.ufpe.cin.if710.parkingapp.db.entity.ParkingDetails
import br.ufpe.cin.if710.parkingapp.db.entity.User

@Database(entities = [Parking::class, ParkingDetails::class, User::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class ParkingAppDatabase : RoomDatabase() {

    abstract fun parkingDetailsDao(): ParkingDetailsDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: ParkingAppDatabase? = null
        private const val DB_NAME = "parking-app-db"

        fun getInstance(application: Application): ParkingAppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =  Room.databaseBuilder(application,
                    ParkingAppDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            ParkingAppDatabase.INSTANCE?.let {
                                ParkingAppDatabase.prePopulate(it)
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        private fun prePopulate(database: ParkingAppDatabase) {
            for (parkingDetails in DataGenerator.parkingDetailsList) {
                AsyncTask.execute { database.parkingDetailsDao().insert(parkingDetails) }
            }
            for (user in DataGenerator.userList) {
                AsyncTask.execute { database.userDao().insert(user) }
            }
        }

    }

}