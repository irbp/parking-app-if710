package br.ufpe.cin.if710.parkingapp.viewmodel

import android.arch.lifecycle.ViewModel
import android.text.Editable
import br.ufpe.cin.if710.parkingapp.network.api.ApiService
import br.ufpe.cin.if710.parkingapp.network.api.model.SignInRequest
import br.ufpe.cin.if710.parkingapp.network.api.model.SignUpResponse
import br.ufpe.cin.if710.parkingapp.utils.inject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class SignInViewModel : ViewModel() {

    private val apiService by inject<ApiService>()

    fun signIn(email: Editable, password: Editable): Observable<Response<SignUpResponse>> {
        return apiService
            .signIn(SignInRequest(email.toString(), password.toString()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun createSession() {

    }

}