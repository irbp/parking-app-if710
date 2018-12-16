package br.ufpe.cin.if710.parkingapp.viewmodel

import android.arch.lifecycle.*
import br.ufpe.cin.if710.parkingapp.network.api.ApiService
import br.ufpe.cin.if710.parkingapp.network.api.model.SignUpData
import br.ufpe.cin.if710.parkingapp.utils.inject

class SignUpViewModel : ViewModel() {

    private val apiService by inject<ApiService>()

    fun signUp(name: String, email: String, password: String) {
        return apiService.signUp(SignUpData(name, email, password))
    }

}