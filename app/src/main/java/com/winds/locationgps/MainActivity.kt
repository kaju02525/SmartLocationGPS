package com.winds.locationgps

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.winds.smartlocationgps.SmartLocation
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    private fun onLocationUpdate(location: Location) {
        tv.text="Latitude: ${location.latitude}\tLongitude: ${location.longitude}"
        tvTime.text = getCurrentTimeString()
        Log.d(TAG, "Latitude: ${location.latitude}\tLongitude: ${location.longitude}")
    }

    private fun onError(error: Throwable?) {
        Log.d(TAG, "onError: "+ error?.message)
    }

    fun startUpdates(v: View) {
        SmartLocation.configure {
            shouldResolveRequest = true
        }
        SmartLocation.startLocationUpdates(this) { result ->
            result.location?.let(::onLocationUpdate)
            result.error?.let(::onError)
        }
    }

    fun stopUpdates(v: View) {
        SmartLocation.stopLocationUpdates()
        tv.text=""
    }


    private fun getCurrentTimeString(): String {
        val calendar = Calendar.getInstance()
        return "${calendar.get(Calendar.HOUR_OF_DAY)} : ${calendar.get(Calendar.MINUTE)} : ${calendar.get(
            Calendar.SECOND
        )}"
    }
}
