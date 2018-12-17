package br.ufpe.cin.if710.parkingapp

import android.app.Application
import android.util.Log
import com.squareup.leakcanary.LeakCanary

class ParkingApp : Application() {

    fun getDataRepository() = DataRepository(this)

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        DIModule.initialize(this)
    }
}