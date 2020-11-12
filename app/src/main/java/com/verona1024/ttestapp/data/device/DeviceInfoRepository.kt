package com.verona1024.ttestapp.data.device

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import com.verona1024.ttestapp.presentation.TestAppApplication

class DeviceInfoRepository {
    @SuppressLint("MissingPermission")
    fun getImei(): String {
        var imei = ""
        val context = TestAppApplication.applicationContext()
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            imei = telephonyManager.imei
        }

        return imei
    }
}