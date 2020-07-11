package com.example.rentmyride

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.net.Inet4Address

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button_login : Button = findViewById(R.id.button_login2)

        button_login.setOnClickListener { loginRequest() }
    }

    private fun loginRequest(){
        val email = findViewById<TextView>(R.id.editText_email)
        val password = findViewById<TextView>(R.id.editText_password)

        val emailString = email.text.toString()
        val passwordString = password.text.toString()

        if(emailString != "" && passwordString != ""){



            var obj = JSONObject()
            obj.put("email", emailString)
            obj.put("password", passwordString)
            obj.put("ip", "127.0.0.1")
            val url = "https://stage.rentmyride.co.za/api/login"

            println(obj.toString())

            val request = JsonObjectRequest(Request.Method.POST, url, obj,
                Response.Listener { response ->
                    // Process the json
                    try {
                        val name = response.getJSONObject("data").getString("name")
                        println(name)

                        intent = Intent(this, WelcomeActivity::class.java)
                        intent.putExtra("name", name)

                        startActivity(intent)

                    } catch (e: Exception) {
                        println("Exception: $e")
                    }

                }, Response.ErrorListener {
                    if (it.toString() == "com.android.volley.AuthFailureError"){
                        println("Auth error")
                        Toast.makeText(applicationContext, "Wrong email or password", Toast.LENGTH_LONG).show()
                    }else {
                        println("Volley error: $it")
                    }
                }
            )

            // Volley request policy, only one time request to avoid duplicate transaction
            request.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                // 0 means no retry
                0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

            // Add the volley post request to the request queue
            VolleySingleton.getInstance(this).addToRequestQueue(request)

        }else{
            Toast.makeText(applicationContext, "Enter your email and password", Toast.LENGTH_LONG).show()
        }
    }
}
