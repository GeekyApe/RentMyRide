package com.example.rentmyride

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.arch.core.executor.TaskExecutor

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val name: String = intent.getStringExtra("name").toString()

        findViewById<TextView>(R.id.textView_welcome_message).text = "Welcome\n$name"
    }
}
