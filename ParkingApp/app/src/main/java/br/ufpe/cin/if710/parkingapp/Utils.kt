package br.ufpe.cin.if710.parkingapp

import android.app.Application
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.util.Patterns
import android.widget.EditText
import br.ufpe.cin.if710.parkingapp.utils.inject

object Utils {
    private val application by inject<Application>()

    fun isFieldEmpty(text: Editable?): Boolean = text == null

    fun isFieldEmpty(text: String): Boolean = text == ""

    fun isPasswordValid(text: Editable?): Boolean = text?.length!! >= 6

    fun isPasswordValid(text: String): Boolean = text?.length!! >= 6

    fun isEmailValid(text: Editable?): Boolean = Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()

    fun isEmailValid(text: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(text).matches()

    val emailValidator: (email: String) -> Error? = {
        if (Utils.isFieldEmpty(it)) {
            Error(application.getString(R.string.error_field_required))
        } else if (!Utils.isEmailValid(it)) {
            Error(application.getString(R.string.error_invalid_email))
        } else {
            null
        }
    }

    val passwordValidator: (password: String) -> Error? = {
        if (Utils.isFieldEmpty(it)) {
            Error(application.getString(R.string.error_field_required))
        } else if (!Utils.isPasswordValid(it)) {
            Error(application.getString(R.string.error_invalid_password))
        } else {
            null
        }
    }


    fun bindValidator(textInput: TextInputEditText, validator: (String) -> Error?){
        textInput.setOnKeyListener { _, _, _ ->
            updateError(textInput, validator)
        }
    }

    fun bindValidator(textInput: EditText, validator: (String) -> Error?){
        textInput.setOnKeyListener { _, _, _ ->
            updateError(textInput, validator)
        }
    }

    private fun updateError(textInput: EditText, validator: (String) -> Error?): Boolean{
        val error = validator(textInput.text.toString())
        if (error != null) {
            textInput.error = error.message
        } else {
            textInput.error = null
        }
        return false
    }


    private fun updateError(textInput: TextInputEditText, validator: (String) -> Error?): Boolean{
        val error = validator(textInput.text.toString())
        if (error != null) {
            textInput.error = error.message
        } else {
            textInput.error = null
        }
        return false
    }

}