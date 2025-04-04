package com.example.lab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val maxProgress = 1000
        val buttonText = "Click me!"
        var variable = 0;
        val textView = findViewById<TextView>(R.id.textView)
        val startButton = findViewById<Button>(R.id.button)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        seekBar.max = maxProgress
        startButton.text = buttonText;


        startButton.setOnClickListener{
            val job = lifecycleScope.launch {
                if (variable >= seekBar.progress)
                {
                    variable = 0;
                }
                Log.d("tojakr", "Job started")
                while (variable < seekBar.progress) {
                    progressBar.max = seekBar.progress
                    variable += 1
                    delay(100)
                    launch(Dispatchers.Main) {
                        textView.text = "Current progress: ${variable}."
                        progressBar.progress = variable
                    }
                }
                launch(Dispatchers.Main) {
                    textView.text = "DONE!"
                }

            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}