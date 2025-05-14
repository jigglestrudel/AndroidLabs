package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var table : IntArray = IntArray(100) { 0 }
    private var table_before : IntArray = IntArray(100) { 0 }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            generateRandomNumbers()
            binding.sampleText.text = table.contentToString()
        }

        binding.button2.setOnClickListener {
            table = table_before.clone()

            val startTime = System.nanoTime()

            table.sortDescending()

            val endTime = System.nanoTime()

            binding.sampleText.text = buildString {
                append(table_before.contentToString())
                append(" -> ")
                append(table.contentToString())
            }

            binding.textView.text = buildString {
                append("sorting took ")
                append(endTime - startTime)
                append(" ns")
            }
        }

        binding.button3.setOnClickListener {
            table = table_before.clone()

            val startTime = System.nanoTime()

            sortArray(table)

            val endTime = System.nanoTime()

            binding.sampleText.text = buildString {
                append(table_before.contentToString())
                append(" -> ")
                append(table.contentToString())
            }

            binding.textView.text = buildString {
                append("sorting took ")
                append(endTime - startTime)
                append(" ns")
            }
        }

        // Example of a call to a native method
        // binding.sampleText.text = stringFromJNI()
    }

    private fun generateRandomNumbers() {
        table = IntArray(100) { (-1000..1000).random() }
        table_before = table.clone()
    }

    /**
     * A native method that is implemented by the 'myapplication' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun sortArray(array: IntArray)

    companion object {
        // Used to load the 'myapplication' library on application startup.
        init {
            System.loadLibrary("myapplication")
        }
    }
}