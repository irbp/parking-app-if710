package br.ufpe.cin.if710.parkingapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.ufpe.cin.if710.parkingapp.R
import br.ufpe.cin.if710.parkingapp.Utils
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        et_signin_email.setOnKeyListener { _, _, _ -> checkEmail() }

        et_signin_password.setOnKeyListener { _, _, _ -> checkPassword() }
    }

    private fun checkEmail(): Boolean {
        if (Utils.isFieldEmpty(et_signin_email.text)) {
            ti_signin_email.error = getString(R.string.error_field_required)
        } else if (!Utils.isEmailValid(et_signin_email.text)) {
            ti_signin_email.error = getString(R.string.error_invalid_email)
        } else {
            ti_signin_email.error = null
        }

        return false
    }

    private fun checkPassword(): Boolean {
        if (Utils.isFieldEmpty(et_signin_password.text)) {
            ti_signin_password.error = getString(R.string.error_field_required)
        } else if (!Utils.isPasswordValid(et_signin_password.text)) {
            ti_signin_password.error = getString(R.string.error_invalid_password)
        } else {
            ti_signin_password.error = null
        }

        return false
    }
}
