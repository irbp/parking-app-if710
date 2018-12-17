package br.ufpe.cin.if710.parkingapp.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.ufpe.cin.if710.parkingapp.viewmodel.ParkingListViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton


@Singleton
class ViewModelFactory: ViewModelProvider.Factory {
    @Inject
    lateinit var parkingListProvider : Provider<ParkingListViewModel>


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParkingListViewModel::class.java)) {
            return parkingListProvider.get() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}