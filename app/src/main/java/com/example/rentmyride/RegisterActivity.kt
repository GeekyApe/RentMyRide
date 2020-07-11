package com.example.rentmyride

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class RegisterActivity : AppCompatActivity() {

    private var t_c_accepted_at = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val butt = findViewById<Button>(R.id.button_register_2)
        butt.setOnClickListener {
            this.Register()
        }

        findViewById<CheckBox>(R.id.checkBox_tc).setOnCheckedChangeListener { _, _ ->
            t_c_accepted_at = SimpleDateFormat("yyyy-M-dd").format((Date()))
        }
    }

    private fun Register() {
        val email = findViewById<TextView>(R.id.editText_email_register)
        val first_name = findViewById<TextView>(R.id.editText_name_register)
        val last_name = findViewById<TextView>(R.id.editText_lastName_register)
        val mobile = findViewById<TextView>(R.id.editText_phoneNumber_register)
        var name = ""
        val password = findViewById<TextView>(R.id.editText_password_register)
        val password_confirmation = findViewById<TextView>(R.id.editText_passwordConf_register)
        val physical_address = findViewById<TextView>(R.id.editText_address_register)
        val rating = findViewById<TextView>(R.id.editText_rating_register)
        val t_c_accepted_version = 2;

        if (email.text.toString() != ""
            && first_name.text.toString() != ""
            && last_name.text.toString() != ""
            && mobile.text.toString() != ""
            && password.text.toString() != ""
            && password_confirmation.text.toString() != ""
            && physical_address.text.toString() != ""
            && rating.text.toString() != ""
        ) {
            if (password.text.toString() == password_confirmation.text.toString()) {
                if (findViewById<CheckBox>(R.id.checkBox_tc).isChecked) {//check tc

                    name = first_name.text.toString() + " " + last_name.text.toString()

                    val obj: JSONObject = JSONObject();
                    obj.put("email", email.text.toString())
                    obj.put("first_name", first_name.text.toString())
                    obj.put("last_name", last_name.text.toString())
                    obj.put("mobile", mobile.text.toString())
                    obj.put("name", name)
                    obj.put("password", password.text.toString())
                    obj.put("password_confirmation", password_confirmation.text.toString())
                    obj.put("physical_address", physical_address.text.toString())
                    obj.put("rating", rating.text.toString())
                    obj.put("t_c_accepted_at", t_c_accepted_at)
                    obj.put("t_c_accepted_version", 2.toString())

                    println(obj.toString())

                    val credentials = ":"
                    val url = "https://stage.rentmyride.co.za/api/register"

                    val request = CustomJsonObjectRequestBasicAuth(Request.Method.POST, url, obj,
                        Response.Listener { response ->
                            // Process the json
                            try {
                                if (response.getString("message") == "Could not register user.") {
                                    Toast.makeText(
                                        applicationContext,
                                        response.getString("message"),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        response.getString("message"),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    intent = Intent(this, WelcomeActivity::class.java)
                                    intent.putExtra("name", name)
                                    startActivity(intent)
                                }
                            } catch (e: Exception) {
                                println("Exception: $e")
                            }

                        }, Response.ErrorListener {
                            println("Volley error: $it")
                        }, credentials
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

                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please accept Terms and conditions",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(applicationContext, "Passwords does not match", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(applicationContext, "Please fill in all fields", Toast.LENGTH_LONG)
                .show()
        }

    }
}