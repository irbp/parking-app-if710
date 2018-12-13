package br.ufpe.cin.if710.parkingapp.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.ufpe.cin.if710.parkingapp.R
import br.ufpe.cin.if710.parkingapp.network.api.ApiService
import br.ufpe.cin.if710.parkingapp.utils.inject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val ParkingApi by inject<ApiService>()
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("Result", "Result is chegou")

        disposable = ParkingApi.getAllParkingsAround(8, 8, 2000)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                    result ->
                Log.d("Result", "Result is $result")
            }, { error ->
                Log.d("Result", "error is ${error.message}")
            })

//        startActivity(Intent(this@MainActivity, ParkingListActivity::class.java))
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
