package com.example.rentmyride

import android.util.Base64
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

//https://android--code.blogspot.com/2019/02/android-kotlin-volley-basic.html
// Class to make a volley json object request with basic authentication
class CustomJsonObjectRequestBasicAuth(
    method:Int, url: String,
    jsonObject: JSONObject?,
    listener: Response.Listener<JSONObject>,
    errorListener: Response.ErrorListener,
    credentials:String
)
    : JsonObjectRequest(method,url, jsonObject, listener, errorListener) {

    private var mCredentials:String = credentials

    @Throws(AuthFailureError::class)
    override fun getHeaders(): Map<String, String> {
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        //val credentials:String = "username:password"
        val auth = "Basic " + Base64.encodeToString(mCredentials.toByteArray(),
            Base64.NO_WRAP)
        headers["Authorization"] = auth
        return headers
    }
}

