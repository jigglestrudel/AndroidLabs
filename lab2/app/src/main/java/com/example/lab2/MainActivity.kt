package com.example.lab2

import android.adservices.customaudience.RemoveCustomAudienceOverrideRequest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val myButton = findViewById<Button>(R.id.button)
        val myCustomView = findViewById<CustomView>(R.id.view2)
        val myAlarmButton = findViewById<Button>(R.id.button3)

        var switchedColours = false;

        val startForResultActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val returnedValue = data?.getBooleanExtra("SwitchChecked", false)
                switchedColours = returnedValue == true;
                myCustomView.switchedColours = switchedColours;
            }
        }

        val secondActivityIntent = Intent(this, SecondActivity::class.java)

        myButton.setOnClickListener {
            secondActivityIntent.putExtra("switchedColours", switchedColours)
            startForResultActivity.launch(secondActivityIntent)
        }

        myAlarmButton.setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM )
            startActivity(intent)
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}