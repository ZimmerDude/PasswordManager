package com.aadesh.passwordmanager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import kotlin.*

class password : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        val fileName = findViewById<EditText>(R.id.editFile)
        val fileData = findViewById<EditText>(R.id.editData)

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnView = findViewById<Button>(R.id.btnView)

        btnSave.setOnClickListener(View.OnClickListener {
            val file: String = fileName.text.toString()
            val data: String = fileData.text.toString()
            val fileOutputStream: FileOutputStream
            try {
                fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            println(fileData.text)
            val filedata = fileData.text.toString()
            val filename = fileName.text.toString()

            if (filename == "") {
                Toast.makeText(
                    applicationContext,
                    "Account Name/Password Cannot be Blank",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(applicationContext, "Password Saved", Toast.LENGTH_LONG).show()
                fileName.text.clear()
                fileData.text.clear()
            }
        })

        btnView.setOnClickListener(View.OnClickListener {
            val filename = fileName.text.toString()
            try {
                if (filename.toString() != null && filename.toString().trim() != "") {
                    var fileInputStream: FileInputStream? = null

                    fileInputStream = openFileInput(filename)
                    var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                    val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                    val stringBuilder: StringBuilder = StringBuilder()
                    var text: String? = null
                    while ({ text = bufferedReader.readLine(); text }() != null) {
                        stringBuilder.append(text)
                    }
                    fileData.setText(stringBuilder.toString())
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Account Name/Password Cannot be Blank",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: FileNotFoundException) {
                fileName.text.clear()
                fileData.text.clear()
                Toast.makeText(applicationContext, "Password Does not Exist", Toast.LENGTH_LONG)
                    .show()
            }

        })

    }
}
