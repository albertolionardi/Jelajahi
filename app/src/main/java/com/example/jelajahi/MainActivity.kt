package com.example.jelajahi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Handle Home navigation
                    true
                }
                R.id.navigation_search -> {
                    // Handle Search navigation
                    true
                }
                R.id.navigation_notifications -> {
                    // Handle Notifications navigation
                    true
                }
                R.id.navigation_account -> {
                    // Handle Account navigation
                    val isLoggedIn = checkIfUserIsLoggedIn()  // Replace with your actual login check
                    if (isLoggedIn) {
                        showUserProfile()
                    } else {
                        showLoginScreen()
                    }
                    true
                }
                R.id.navigation_tracker -> {
                    // Handle Tracker navigation
                    val intent = Intent(this, TrackerActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun checkIfUserIsLoggedIn(): Boolean {
        // Implement your logic to check if the user is logged in
        // This might involve checking SharedPreferences, a database, or an API call
        return false  // Replace with actual login check
    }

    private fun showUserProfile() {
        // Navigate to the profile screen or update the UI to show the user's profile
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun showLoginScreen() {
        // Navigate to the login screen or show a login dialog
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}