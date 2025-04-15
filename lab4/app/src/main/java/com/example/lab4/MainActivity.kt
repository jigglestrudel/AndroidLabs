package com.example.lab4

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var timeTextView: TextView

    private var timeThread: Thread? = null
    private var running = true

    private lateinit var mService: MyService
    private var mBound: Boolean = false
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName,
                                        service: IBinder
        ) {
            Log.d("MainActivity", "Service connected")
            val binder = service as MyService.LocalBinder
            mService = binder.getService()
            mBound = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MyService::class.java)
        startService(intent)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        startTimeThread()
    }

    private fun startTimeThread() {
        running = true
        timeThread = Thread {
            try{
            while (running) {
                if (mBound) {
                    val time = mService.getTime() / 1000
                    runOnUiThread {
                        timeTextView.text = "Time: $time s"
                    }
                }
                Thread.sleep(1000)
            }
            } catch (e: InterruptedException) {
                // Handle cleanup here if needed
                Log.d("MainActivity", "Time thread interrupted")
            }
        }
        timeThread?.start()
    }

    override fun onStop() {
        super.onStop()
        running = false
        timeThread?.interrupt()
        if (mBound) {
            unbindService(serviceConnection)
            mBound = false
        }
    }

    private fun getPermissions() {
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_DENIED
            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,arrayOf(POST_NOTIFICATIONS), 0)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions,
            grantResults)
        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Notifications Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Notifications Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        timeTextView = findViewById(R.id.textView)
        getPermissions()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}