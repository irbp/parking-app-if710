package br.ufpe.cin.if710.parkingapp

import android.app.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.text.Editable
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
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

    private const val NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID + ".channel"

    fun getUniqueId() = ((System.currentTimeMillis() % 10000).toInt())

    fun makeNotification(context: Context, message: String): Notification {
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            && notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
            val name = context.getString(R.string.app_name)
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_MIN)

            notificationManager.createNotificationChannel(channel)
        }

//        val intent = MainActivity.newIntent(context.applicationContext, latLng)
//
//        val stackBuilder = TaskStackBuilder.create(context)
//            .addParentStack(MainActivity::class.java)
//            .addNextIntent(intent)
//        val notificationPendingIntent = stackBuilder
//            .getPendingIntent(getUniqueId(), PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(message)
            .setAutoCancel(true)
            .build()

        return notification
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