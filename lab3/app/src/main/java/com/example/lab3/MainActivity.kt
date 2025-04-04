package com.example.lab3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ksg.mso.filehelp.FilesHelper
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.filecountertextview).text = "${getFileList().size} file(s)"
        buildRecyclerList()

        val newFileButton = findViewById<Button>(R.id.newfilebutton)
        newFileButton.setOnClickListener {
            val editingActivity = Intent(this, FileEditingActivity::class.java)
            //editingActivity.putExtra("fileName", "new_file.txt")
            editingActivity.putExtra("isNewFile", true)
            startActivity(editingActivity)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getFileList() : List<String>
    {
        return filesDir.listFiles()?.map { it.name }?.filter { name -> name != "profileInstalled" } ?: emptyList()
    }

    private fun buildRecyclerList()
    {
        val fileList = getFileList()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FileListAdapter(fileList) {
            fileName -> sendFileToEditActivity(fileName)
        }
    }

    private fun sendFileToEditActivity(fileName: String)
    {
        Log.d("Files", "sending $fileName to editing")
        val editingActivity = Intent(this, FileEditingActivity::class.java)
        editingActivity.putExtra("fileName", fileName)
        startActivity(editingActivity)
    }


    override fun onResume() {
        super.onResume()
        buildRecyclerList()
        findViewById<TextView>(R.id.filecountertextview).text = "${getFileList().size} file(s)"
        Log.d("Files", "Files updated")
    }
}