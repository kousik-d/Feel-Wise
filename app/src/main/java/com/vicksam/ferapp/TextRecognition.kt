package com.vicksam.ferapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class TextRecognition : AppCompatActivity() {
    lateinit var UserText : TextView
    lateinit var SendUsButton : AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_reg)
        UserText = findViewById(R.id.feelings)
        SendUsButton = findViewById(R.id.SendTextToAPI)
        SendUsButton.setOnClickListener {
            val feelings = UserText.text.toString()
            Log.i("TEXT_RECOGNITION", "${feelings}")

            if (feelings.isNotEmpty() && isOnlyLettersAndSpaces(feelings)) {
                val client = OkHttpClient()
                val mediaType = "application/json".toMediaTypeOrNull()
                val body = RequestBody.create(mediaType, "{\r \"sentence\": \"${feelings}\"}")
                Log.i("TEXT_RECOGNITION", "${body}")
                val request = Request.Builder()
                    .url("https://emodex-emotions-analysis.p.rapidapi.com/rapidapi/emotions")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader(
                        "X-RapidAPI-Key",
                        "a84ef1a189msh607fd01612eca52p17fe74jsnfac3d03e9b0b"
                    )
                    .addHeader("X-RapidAPI-Host", "emodex-emotions-analysis.p.rapidapi.com")
                    .build()

                Log.i("TEXT_RECOGNITION", "Body is ${body}")

                client.newCall(request).enqueue(object : okhttp3.Callback {
                    override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                        Log.e("TEXT_RECOGNITION", "Error occurred: ${e.message}", e)
                    }

                    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                        val responseData = response.body?.string()
                        val response = client.newCall(request).execute()
                        val responseBody = response.body?.string() ?: ""
                        val resultMap = convertResponseToMap(responseBody)

                        Log.i("TEXT_RECOGNITION", "Response: $resultMap")
                    }
                })
            } else {
                Toast.makeText(this, "Please tell us correctly", Toast.LENGTH_SHORT).show();
            }
        }
    }

    fun isOnlyLettersAndSpaces(input: String): Boolean {
        val regex = Regex("^[a-zA-Z\\s]+$")
        return input.matches(regex)
    }

    fun convertResponseToMap(responseBody: String) : String?{
        val gson = Gson()
        val mapType = object : TypeToken<Map<String, Any>>() {}.type
        val resultMap = gson.fromJson(responseBody, mapType)
        var maxKey: String? = null
        var maxValue: Any? = null

        for ((key, value) in resultMap) {
            if (maxValue == null || (value is Number && (maxValue !is Number || value.toDouble() > (maxValue as Number).toDouble()))) {
                maxKey = key
                maxValue = value
            }
        }
        return maxKey
    }


}