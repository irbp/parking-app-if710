package br.ufpe.cin.if710.parkingapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.text.Editable
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import br.ufpe.cin.if710.parkingapp.receiver.NotificationBroadcastReceiver
import br.ufpe.cin.if710.parkingapp.ui.ParkingListActivity
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

    const val BOOT_NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID + ".channel1"
    const val GEOFENCE_NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID + "channel2"
    const val EXTRA_GEOFENCE_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"

    fun getUniqueId() = ((System.currentTimeMillis() % 10000).toInt())

    fun makeNotification(context: Context, message: String, channelId: String, importance: Int, name: String)
            : Notification {
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            && notificationManager.getNotificationChannel(channelId) == null) {
            val channel = NotificationChannel(channelId, name, importance)

            notificationManager.createNotificationChannel(channel)
        }

//        val intent = MainActivity.newIntent(context.applicationContext, latLng)
//
//        val stackBuilder = TaskStackBuilder.create(context)
//            .addParentStack(MainActivity::class.java)
//            .addNextIntent(intent)
//        val notificationPendingIntent = stackBuilder
//            .getPendingIntent(getUniqueId(), PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(message)
            .setAutoCancel(true)
            .build()

        return notification
    }

    fun showGeofenceNotification(context: Context, message: String) {
        val notificationId = getUniqueId()
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            && notificationManager.getNotificationChannel(GEOFENCE_NOTIFICATION_CHANNEL_ID) == null) {
            val channel = NotificationChannel(GEOFENCE_NOTIFICATION_CHANNEL_ID, "ParkingApp Location Notify",
                NotificationManager.IMPORTANCE_HIGH)

            notificationManager.createNotificationChannel(channel)
        }

        val okIntent = Intent(context, NotificationBroadcastReceiver::class.java).apply {
            action = NotificationBroadcastReceiver.ACTION_ACCEPT_PARKING
            putExtra(EXTRA_GEOFENCE_NOTIFICATION_ID, notificationId)
        }
        val rejectIntent = Intent(context, NotificationBroadcastReceiver::class.java).apply {
            action = NotificationBroadcastReceiver.ACTION_REJECT_PARKING
            putExtra(EXTRA_GEOFENCE_NOTIFICATION_ID, notificationId)
        }
        val okPendingIntent = PendingIntent.getBroadcast(context, 0, okIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val rejectPendingIntent = PendingIntent.getBroadcast(context, 0, rejectIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, GEOFENCE_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Um estacionamento foi localizado")
            .setContentText(message)
            .addAction(R.drawable.ic_check_black_24dp, "Sim", okPendingIntent)
            .addAction(R.drawable.ic_not_interested_black_24dp, "Não", rejectPendingIntent)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_LONG): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    fun checkPermissions(context: Context, permission: String): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(context, permission)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(context: Activity, permission: String) {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(context, permission)
        if (shouldProvideRationale) {
            Snackbar.make(
                context.findViewById(R.id.activity_main),
                context.getString(R.string.permission_rationale),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(context.getString(R.string.allow_permission)) {
                    ActivityCompat.requestPermissions(context, arrayOf(permission), 34)
                }
                .show()
        } else {
            ActivityCompat.requestPermissions(context, arrayOf(permission), 34)
        }
    }

}