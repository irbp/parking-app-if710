package br.ufpe.cin.if710.parkingapp.network.api

import android.app.Application
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

import br.ufpe.cin.if710.parkingapp.R
import br.ufpe.cin.if710.parkingapp.network.model.ParkingListResponse
import br.ufpe.cin.if710.parkingapp.network.model.ParkingResponse
import br.ufpe.cin.if710.parkingapp.utils.inject


interface FirestoreApiService {

    @GET("parkings/")
    fun getAllParkings(): Observable<ParkingListResponse>

    @GET("parkings/{id}")
    fun getParkingById(@Path("id") id: String): Observable<ParkingResponse>

    /**
     * Companion object to create the Firestore API Service
     */
    companion object Factory {
        private val application by inject<Application>()
        private val apiBaseUrl = application.getString(R.string.api_base_url)

        fun create(): FirestoreApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(apiBaseUrl)
                .build()

            return retrofit.create(FirestoreApiService::class.java)
        }
    }


}
