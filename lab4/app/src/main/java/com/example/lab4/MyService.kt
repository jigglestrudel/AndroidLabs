package com.example.lab4

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.lifecycle.LifecycleService

class MyService : LifecycleService() {
    private val binder = LocalBinder()
    private var startTime: Long = 0
    inner class LocalBinder : Binder() {
        fun getService(): MyService = this@MyService
    }
    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }
    override fun onCreate() {
        super.onCreate()
        startTime = System.currentTimeMillis()
        startNotificationChannel()
    // Start long running jobs here.
    }
    override fun onDestroy() {
        super.onDestroy()
        // Cancel any remaining jobs here.
    }

    fun getTime() : Long {
        Log.d("service", "time request")
        return System.currentTimeMillis() - startTime
    }

    fun startNotificationChannel() {
        val CHANNEL_ID = "my_channel_01"
        val channel = NotificationChannel(CHANNEL_ID, "My Channel",
            NotificationManager.IMPORTANCE_DEFAULT)
        (getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager).
        createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(this,
            CHANNEL_ID).build()
        ServiceCompat.startForeground(this, 101, notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
    }
}