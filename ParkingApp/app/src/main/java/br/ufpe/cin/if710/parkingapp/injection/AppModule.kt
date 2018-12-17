package br.ufpe.cin.if710.parkingapp.injection

import android.app.Application
import br.ufpe.cin.if710.parkingapp.db.ParkingAppDatabase
import br.ufpe.cin.if710.parkingapp.db.dao.ParkingDetailsDao
import br.ufpe.cin.if710.parkingapp.db.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * AppModule shall be used initialize objects used across our application,
 * such as Room(Database), Retrofit, Shared Preference, etc.
 */
@Module
internal class AppModule {
    @Singleton
    @Provides
    fun provideDatabase(app: Application): ParkingAppDatabase {
        return ParkingAppDatabase.getInstance(app)
    }

    @Singleton
    @Provides
    fun provideUserDao(db: ParkingAppDatabase): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideParkingDetailsDao(db: ParkingAppDatabase): ParkingDetailsDao {
        return db.parkingDetailsDao()
    }
}