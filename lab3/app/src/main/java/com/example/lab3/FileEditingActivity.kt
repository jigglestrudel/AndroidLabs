package com.example.lab3

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ksg.mso.filehelp.FilesHelper

class FileEditingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_file_editing)

        val filesHelper = FilesHelper()
        val receivedFileName = intent.getStringExtra("fileName")?: ""
        val isNewFile = intent.getBooleanExtra("isNewFile", false)

        val openedFile = filesHelper.preparePrivateFile(this, receivedFileName)

        val editField = findViewById<TextInputEditText>(R.id.textinputtext)
        editField.gravity = Gravity.TOP or Gravity.START
        if (!isNewFile)
        {
            editField.setText(filesHelper.readPrivateFile(openedFile)?.let{String(it, Charsets.UTF_8)}?:"")
        }

        val fileNameField = findViewById<TextInputEditText>(R.id.filenametext)
        fileNameField.setText(receivedFileName)

        val backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener {
            val backActivity = Intent(this, MainActivity::class.java)
            setResult(RESULT_OK, backActivity)
            finish()
        }

        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            if (fileNameField.getText().toString().isNotEmpty())
            {
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
                val savedFile = filesHelper.preparePrivateFile(
                    this,
                    fileNameField.getText().toString()
                )
                filesHelper.writeToPrivateFile(savedFile, editField.getText().toString().toByteArray())
                val backActivity = Intent(this, MainActivity::class.java)
                setResult(RESULT_OK, backActivity)
                finish()
            }
            else {
                Toast.makeText(this, "filename empty", Toast.LENGTH_SHORT).show()
            }

        }

        val deleteButton = findViewById<Button>(R.id.deletebutton)
        if (isNewFile)
        {
            deleteButton.isEnabled = false
        }
        deleteButton.setOnClickListener {
            openedFile.delete()
            val backActivity = Intent(this, MainActivity::class.java)
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