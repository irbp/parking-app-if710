package br.ufpe.cin.if710.parkingapp.ui

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.ufpe.cin.if710.parkingapp.R
import br.ufpe.cin.if710.parkingapp.Utils
import br.ufpe.cin.if710.parkingapp.network.api.Session
import br.ufpe.cin.if710.parkingapp.utils.inject
import br.ufpe.cin.if710.parkingapp.viewmodel.SignInViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.toast

class SignInActivity : AppCompatActivity() {
    private lateinit var viewModel: SignInViewModel

    private val session by inject<Session>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        if (!Utils.checkPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Utils.requestPermissions(this@SignInActivity, Manifest.permission.ACCESS_FINE_LOCATION)
        }

        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)

        Utils.bindValidators(et_signin_email, arrayOf(Utils.notEmptyValidator, Utils.emailValidator))
        Utils.bindValidators(et_signin_password, arrayOf(Utils.notEmptyValidator, Utils.minLengthValidator(6)))

        Utils.disableIfInvalid(btn_signin_login, arrayOf(et_signin_email, et_signin_password))

        txt_signin_create_account.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }

        btn_signin_login.setOnClickListener {
            viewModel
                .signIn(et_signin_email.text, et_signin_password.text)
                .subscribe(
                    {
                        startActivity(Intent(this@SignInActivity, ParkingListActivity::class.java))
                        finish()
                        Log.d("SessionTest", "Chegou")
                    },
                    {
                        toast(getString(R.string.user_signin_error))
                    }
                )
        }
    }

    override fun onResume() {
        super.onResume()
        if (session.getCurrentUserSession() != null){
            startActivity(Intent(this@SignInActivity, ParkingListActivity::class.java))
            finish()
        }
    }

}
