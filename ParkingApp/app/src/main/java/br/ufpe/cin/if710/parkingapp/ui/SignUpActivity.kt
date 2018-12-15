package br.ufpe.cin.if710.parkingapp.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import br.ufpe.cin.if710.parkingapp.R
import br.ufpe.cin.if710.parkingapp.Utils
import br.ufpe.cin.if710.parkingapp.network.api.Session
import br.ufpe.cin.if710.parkingapp.utils.inject
import br.ufpe.cin.if710.parkingapp.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

private lateinit var viewModel: SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private val session by inject<Session>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        Utils.bindValidator(et_signup_email, Utils.emailValidator)
        Utils.bindValidator(et_signup_password, Utils.passwordValidator)

    }

}
