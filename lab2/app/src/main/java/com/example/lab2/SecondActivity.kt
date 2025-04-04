package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        val receivedData = intent.getBooleanExtra("switchedColours", false)

        val checkSwitch = findViewById<Switch>(R.id.switch1)
        checkSwitch.isChecked = receivedData == true;

        val returnButton = findViewById<Button>(R.id.button2)
        returnButton.setOnClickListener {
            val backActivity = Intent(this, MainActivity::class.java)
            backActivity.putExtra("SwitchChecked", checkSwitch.isChecked)
            setResult(RESULT_OK, backActivity)
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}