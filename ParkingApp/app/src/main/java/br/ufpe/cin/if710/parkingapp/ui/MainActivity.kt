package br.ufpe.cin.if710.parkingapp.ui

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.ufpe.cin.if710.parkingapp.R
import br.ufpe.cin.if710.parkingapp.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Utils.checkPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Utils.requestPermissions(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
        }

    }
}