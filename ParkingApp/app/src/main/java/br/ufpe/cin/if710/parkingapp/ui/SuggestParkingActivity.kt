package br.ufpe.cin.if710.parkingapp.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.ufpe.cin.if710.parkingapp.R
import kotlinx.android.synthetic.main.activity_suggest_parking.*

class SuggestParkingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggest_parking)

        btn_suggest_submit.setOnClickListener {
            finish()
        }
    }
}
