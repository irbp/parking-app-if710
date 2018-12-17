package br.ufpe.cin.if710.parkingapp.network.api

import br.ufpe.cin.if710.parkingapp.network.api.model.SignInRequest
import br.ufpe.cin.if710.parkingapp.network.api.model.UserSession
import br.ufpe.cin.if710.parkingapp.utils.inject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.security.spec.InvalidParameterSpecException

enum class SignInOptions { EMAIL }



class Session {
    private val apiService by inject<ApiService>()

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
                            currUserSession = UserSession(email!!)
                        } else {
                            emitter.onError(Throwable())
                        }
                        emitter.onComplete()
                    },
                        {
                            emitter.onError(it)
                            emitter.onComplete()
                        })
            }
        }

        return null
    }

}