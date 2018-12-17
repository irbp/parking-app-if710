package br.ufpe.cin.if710.parkingapp.viewmodel

import android.arch.lifecycle.ViewModel
import android.text.Editable
import br.ufpe.cin.if710.parkingapp.network.api.ApiService
import br.ufpe.cin.if710.parkingapp.network.api.Session
import br.ufpe.cin.if710.parkingapp.network.api.model.SignInRequest
import br.ufpe.cin.if710.parkingapp.network.api.model.SignUpResponse
import br.ufpe.cin.if710.parkingapp.utils.inject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class SignInViewModel : ViewModel() {

    private val apiService by inject<ApiService>()
    private val session by inject<Session>()

    fun signIn(email: Editable, password: Editable): Observable<String> {
        return session
            .signInWithEmail(email.toString(), password.toString())
            .createSession()!!
    }

}