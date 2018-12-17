package br.ufpe.cin.if710.parkingapp.network.api

import android.util.Log
import br.ufpe.cin.if710.parkingapp.DataRepository
import br.ufpe.cin.if710.parkingapp.db.entity.User
import br.ufpe.cin.if710.parkingapp.network.api.model.SignInRequest
import br.ufpe.cin.if710.parkingapp.network.api.model.SignInResponse
import br.ufpe.cin.if710.parkingapp.network.api.model.UserSession
import br.ufpe.cin.if710.parkingapp.utils.inject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.security.spec.InvalidParameterSpecException

enum class SignInOptions { EMAIL }

class Session {
    private val apiService by inject<ApiService>()
    private val dataRepository by inject<DataRepository>()


    var signInOption: SignInOptions?
    var isLoggedIn: Boolean
    var persistent: Boolean
    var currUserSession: UserSession?
    var email: String?
    var password: String?

    init {
        isLoggedIn = false
        persistent = false
        currUserSession = null
        signInOption = null
        email = null
        password = null
    }

    fun persistentSession(persistent: Boolean): Session {
        this.persistent = persistent
        return this
    }

    fun signInWithEmail(email: String, password: String): Session {
        signInOption = SignInOptions.EMAIL
        this.email = email
        this.password = password
        return this
    }

    fun signInWithFacebook(email: String, password: String): Session {
        NotImplementedError("An operation is not implemented.")
        return this
    }

    fun getCurrentUserSession(): UserSession? {
        return currUserSession
    }

    fun createSession(): Observable<String>? {
        if (signInOption == null) throw InvalidParameterSpecException()

        if (signInOption == SignInOptions.EMAIL) {
            return Observable.create { emitter ->
                apiService
                    .signIn(SignInRequest(email!!, password!!))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        if (response.isSuccessful) {
                            emitter.onNext("Session Created")
                            val user = response.body() as SignInResponse
                            currUserSession = UserSession(user.id, user.email)
                            dataRepository.insertUser(
                                User(
                                    user.id,
                                    user.name,
                                    user.email
                                )
                            )
                            Log.d("SessionTest", currUserSession.toString())

                        } else {
                            Log.d("SessionTest", "Error")
                            emitter.onError(Throwable())
                        }
                        emitter.onComplete()
                    },
                        {
                            Log.d("SessionTest", "Error2")

                            emitter.onError(it)
                            emitter.onComplete()
                        })
            }
        }

        return null
    }

}