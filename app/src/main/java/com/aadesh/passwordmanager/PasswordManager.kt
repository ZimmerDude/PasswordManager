package com.aadesh.passwordmanager


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton


class PasswordManager : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_manager)


        val image = findViewById<ImageButton>(R.id.floatingActionButton)
        image.setOnClickListener {
            val intent = Intent(this, password::class.java)
            startActivity(intent)
        }

    }
}