package br.ufpe.cin.if710.parkingapp.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.ufpe.cin.if710.parkingapp.R
import br.ufpe.cin.if710.parkingapp.Utils
import br.ufpe.cin.if710.parkingapp.network.api.Session
import br.ufpe.cin.if710.parkingapp.utils.inject
import br.ufpe.cin.if710.parkingapp.viewmodel.SignUpViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.toast


class SignUpActivity : AppCompatActivity() {
    private val session by inject<Session>()

    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        Utils.bindValidators(et_signup_name, arrayOf(Utils.notEmptyValidator))
        Utils.bindValidators(et_signup_email, arrayOf(Utils.notEmptyValidator, Utils.emailValidator))
        Utils.bindValidators(et_signup_password, arrayOf(Utils.notEmptyValidator, Utils.minLengthValidator(6)))

        Utils.disableIfInvalid(btn_signup, arrayOf(et_signup_name, et_signup_email, et_signup_password))

        btn_signup.setOnClickListener {
            viewModel.signUp(
                et_signup_name.text, et_signup_email.text, et_signup_password.text
            ).subscribe(
                    { response ->
                        if (response.isSuccessful) {
                            toast(R.string.user_created)

                        } else {
                            toast(response.code())
                        }
                    },
                    {
                        toast(getString(R.string.user_creation_error))
                    }
                )
        }
    }

}
