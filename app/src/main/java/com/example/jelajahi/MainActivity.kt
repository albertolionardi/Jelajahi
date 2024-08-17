package com.example.jelajahi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonToMountain = findViewById<Button>(R.id.buttonToMountain)
        buttonToMountain.setOnClickListener {
            val intent = Intent(this, MountainActivity::class.java)
            startActivity(intent)
        }

        val buttonToLogin = findViewById<Button>(R.id.buttonToLogin)
        buttonToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}