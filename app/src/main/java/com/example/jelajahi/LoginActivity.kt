package com.example.jelajahi
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPasswordText: TextView
    private lateinit var signUpText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acivity_login)

        // Initialize the views
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        forgotPasswordText = findViewById(R.id.forgotPassword)
        signUpText = findViewById(R.id.signUp)

        // Set click listener on the login button
        loginButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            // Validate the inputs
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            } else {
                // Perform login action (this is where you'd typically call an API)
                performLogin(username, password)
            }
        }

        // Set click listener on forgot password text
        forgotPasswordText.setOnClickListener {
            // Handle forgot password action
            Toast.makeText(this, "Forgot Password clicked", Toast.LENGTH_SHORT).show()
        }

        // Set click listener on sign up text
        signUpText.setOnClickListener {
            // Handle sign up action, such as navigating to a sign-up screen
            Toast.makeText(this, "Sign Up clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun performLogin(username: String, password: String) {
        // This is a stub for performing login
        // In a real app, you'd call your API here and handle the response
        if (username == "user" && password == "password") {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            // Navigate to the next screen, for example, a home screen
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }
}