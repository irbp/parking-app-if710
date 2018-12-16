package br.ufpe.cin.if710.parkingapp

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import br.ufpe.cin.if710.parkingapp.utils.inject

typealias Validator = (String) -> Error?

object Utils {

    fun TextView.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cb(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private val application by inject<Application>()

    fun isFieldEmpty(text: String): Boolean = text == ""

    fun isPasswordValid(text: String): Boolean = text.length >= 6

    fun isEmailValid(text: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(text).matches()

    val notEmptyValidator: Validator = {
        if (Utils.isFieldEmpty(it)) {
            Error(application.getString(R.string.error_field_required))
        } else {
            null
        }
    }

    val emailValidator: Validator = {
        if (!Utils.isEmailValid(it)) {
            Error(application.getString(R.string.error_invalid_email))
        } else {
            null
        }
    }

    val passwordValidator: Validator = {
        if (Utils.isPasswordValid(it)) {
            Error(application.getString(R.string.error_invalid_password))
        } else {
            null
        }
    }


    fun minLengthValidator(minLength: Int): Validator {
        return {
            if (it.length < minLength) {
                Error("Quantidade de caracteres precisa ser maior que $minLength")
            } else {
                null
            }
        }
    }

    fun bindValidators(textInput: TextView, validator: Array<Validator>) {
        updateError(textInput, validator)

        textInput.onChange {
            updateError(textInput, validator)
        }
    }

    private fun updateError(textInput: TextView, validator: Array<(String) -> Error?>) {
        validator.forEach {validator ->
            val error = validator(textInput.text.toString())
            if (error != null) {
                textInput.error = error.message
            } else {
                textInput.error = null
            }
        }
    }

    fun textViewArrayInvalid(textViews: Array<TextView>): Boolean {
        var hasInvalid = false
        textViews.forEach { textView ->
            if (textView.error != null) {
                hasInvalid = true
            }
        }
        return hasInvalid
    }

    fun disableIfInvalid(btn: Button, textViews: Array<TextView>) {
        btn.isEnabled = !textViewArrayInvalid(textViews)

        textViews.forEach {textView ->
            textView.onChange {
                btn.isEnabled = !textViewArrayInvalid(textViews)
            }
        }
    }

}