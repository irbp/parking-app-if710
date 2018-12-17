package br.ufpe.cin.if710.parkingapp

import android.app.Application
import android.app.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns

import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView

import android.widget.EditText
import android.widget.Toast

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