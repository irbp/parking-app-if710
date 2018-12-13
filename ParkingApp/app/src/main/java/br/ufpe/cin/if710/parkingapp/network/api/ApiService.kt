package br.ufpe.cin.if710.parkingapp.network.api

import android.app.Application
import br.ufpe.cin.if710.parkingapp.R
import br.ufpe.cin.if710.parkingapp.network.api.model.Parking
import br.ufpe.cin.if710.parkingapp.utils.inject
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("parkings")
    fun getParkingInside(
        @Query("lng") longitude: Number,
        @Query("lat") latitude: Number): Observable<Parking>

    @GET("parkings")
    fun getAllParkingsAround(
        @Query("lng") longitude: Number,
        @Query("lat") latitude: Number,
        @Query("radius") radius: Number): Observable<Array<Parking>>

    @GET("parkings/{id}")
    fun getParkingById(@Path("id") id: String): Observable<Parking>

    /**
     * Companion object to create the API Service
     */
    companion object Factory {
        private val application by inject<Application>()
        private val apiBaseUrl = application.getString(R.string.api_base_url)

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(apiBaseUrl)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }


}