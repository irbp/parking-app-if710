package br.ufpe.cin.if710.parkingapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.ufpe.cin.if710.parkingapp.R
import br.ufpe.cin.if710.parkingapp.Utils
import br.ufpe.cin.if710.parkingapp.network.api.Session
import br.ufpe.cin.if710.parkingapp.utils.inject
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private val session: Session by inject<Session>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        Utils.bindValidator(et_signin_email, Utils.emailValidator)
        Utils.bindValidator(et_signin_password, Utils.passwordValidator)
    }

}
