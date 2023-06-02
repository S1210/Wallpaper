package com.akvelon.wallpaper.controller.network

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.akvelon.wallpaper.extension.isInternetAvailable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkController @Inject constructor(
    private val application: Application
) {

    val connectivityFlow = callbackFlow {
        val receiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context?, intent: Intent?) {
                trySendBlocking(context?.isInternetAvailable() == true)
            }
        }
        application.registerReceiver(
            receiver,
            IntentFilter().apply {
                addAction("android.net.conn.CONNECTIVITY_CHANGE")
            }
        )
        awaitClose { application.unregisterReceiver(receiver) }
    }

}
