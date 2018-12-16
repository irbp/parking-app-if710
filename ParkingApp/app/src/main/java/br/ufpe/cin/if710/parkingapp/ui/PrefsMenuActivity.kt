package br.ufpe.cin.if710.parkingapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

import br.ufpe.cin.if710.parkingapp.R

class PrefsMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefs_menu)
    }

    class ParkingPreferenceFragment : PreferenceFragment() {
        private var listener: SharedPreferences.OnSharedPreferenceChangeListener? = null
        private var closureNotificationPreference: Preference? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.parking_prefs)


            closureNotificationPreference = preferenceManager.findPreference("pref_closure_notification_time")

            listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                Log.d("SharedPreference", key)
                closureNotificationPreference!!.summary = sharedPreferences.getString(
                        key, "Nenhuma opção selecionada")
            }

        }
    }
}