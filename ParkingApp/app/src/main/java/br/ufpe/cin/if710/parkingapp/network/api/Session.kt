package br.ufpe.cin.if710.parkingapp.network.api

import br.ufpe.cin.if710.parkingapp.db.entity.User
import br.ufpe.cin.if710.parkingapp.network.api.model.SignInRequest
import br.ufpe.cin.if710.parkingapp.network.api.model.SignUpResponse
import br.ufpe.cin.if710.parkingapp.network.api.model.UserSession
import br.ufpe.cin.if710.parkingapp.utils.inject
import io.reactivex.Observable
import retrofit2.Response

class Session {
    private val apiService by inject<ApiService>()

    var isLoggedIn: Boolean
    var persistent: Boolean
    var currUserSession: UserSession?

    init {
        isLoggedIn = false
        persistent = false
        currUserSession = null
    }

    fun persistentSession(persistent: Boolean): Session {
        this.persistent = persistent
        return this
    }

    fun signInWithEmail(email: String, password: String): Observable<Response<SignUpResponse>> {
        return apiService.signIn(SignInRequest(email, password))
    }

    fun signInWithFacebook(email: String, password: String): Session {
        NotImplementedError("An operation is not implemented.")
        return this
    }

    fun signOut() {

    }

    fun getCurrentUserSession(): UserSession?{
        NotImplementedError("An operation is not implemented.")
        return null
    }

    fun createSession(){

    }

}