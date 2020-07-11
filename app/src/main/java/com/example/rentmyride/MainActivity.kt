package com.example.rentmyride

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button_login : Button = findViewById(R.id.button_login)
        val button_register : Button = findViewById(R.id.button_register)

        button_login.setOnClickListener { Login() }
        button_register.setOnClickListener { RegisterMain() }
    }

    private fun Login(){
        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun RegisterMain(){
        intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
