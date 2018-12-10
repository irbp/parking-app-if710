package br.ufpe.cin.if710.parkingapp

import android.text.Editable
import android.util.Patterns

object Utils {

    fun isFieldEmpty(text: Editable?): Boolean = text == null

    fun isPasswordValid(text: Editable?): Boolean = text?.length!! >= 6

    fun isEmailValid(text: Editable?): Boolean = Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()
}