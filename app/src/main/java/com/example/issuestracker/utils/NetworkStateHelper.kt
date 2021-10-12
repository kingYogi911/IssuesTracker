package com.example.issuestracker.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData

class NetworkStateHelper(private val context: Context) : LiveData<Boolean>() {
    private val cm: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val x = isOnline()
            Log.d(TAG, "Network Status " + if (x) "Connected" else "Disconnected")
            postValue(x)
        }
    }
    private val networkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.d(TAG, "Network Status Connected")
            postValue(true)
        }

        override fun onLost(network: Network) {
            Log.d(TAG, "Network Status Disconnected")
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            context.registerReceiver(
                networkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        } else {
            cm.registerDefaultNetworkCallback(networkCallBack)
        }
    }

    override fun onInactive() {
        super.onInactive()
        try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                context.unregisterReceiver(networkReceiver)
            } else {
                cm.unregisterNetworkCallback(networkCallBack)
            }
        } catch (e: Exception) {
            //do nothing this exception can be invoked when unregistering receiver
            //that is not registered yet
            //possible reason could be the object is in inactive state initially or while state/orientation change of device
        }
    }

    private fun isOnline(): Boolean {
        return cm.activeNetworkInfo.let {
            it?.isConnectedOrConnecting == true
        }
    }

    companion object {
        const val TAG = "MyHelper"
    }
}