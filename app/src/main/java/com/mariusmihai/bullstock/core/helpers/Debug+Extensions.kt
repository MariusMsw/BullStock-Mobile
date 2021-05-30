package com.mariusmihai.bullstock.core.helpers

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.mariusmihai.bullstock.BuildConfig
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

fun String.printMessage(TAG: String? = null) {
    if (BuildConfig.DEBUG) {
        Log.e(TAG, this)
    }
}

fun String.toastShortError(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    if (BuildConfig.DEBUG) {
        Toast.makeText(context, this, duration).show()
    }
}

fun String.toastLongError(context: Context, duration: Int = Toast.LENGTH_LONG) {
    if (BuildConfig.DEBUG) {
        Toast.makeText(context, this, duration).show()
    }
}

fun String.printMessageAndToast(
    context: Context,
    TAG: String? = null,
    duration: Int = Toast.LENGTH_SHORT
) {
    if (BuildConfig.DEBUG) {
        printMessage(TAG)
        toastShortError(context, duration)
    }
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun toDate(milliseconds: Long): String {
    if (milliseconds == 0L) return "None"
    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)
    return simpleDateFormat.format(milliseconds).toString()
}