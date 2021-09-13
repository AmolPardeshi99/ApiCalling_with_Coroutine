package com.example.apicalling_with_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var UserList: List<ResponseModelItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {

            val res: String = async {
                var data = getApiData()
                return@async "$data"
            }.await()

            CoroutineScope(Dispatchers.Main).launch {
                //tvText2.text = res.toString()
                Log.d("onCreate", "onCreate: $res")
            }
        }
    }

    fun getApiData(): String {
        val apiService = Network.getInstance().create(ApiService::class.java)
        apiService.getUserData().enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                response.body()?.run {
                    UserList = response.body()!!
                    tvText1.text = response.body()?.get(1)?.name.toString()
                    tvText2.text = response.body()?.get(1)?.language.toString()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, "API calling failed", Toast.LENGTH_LONG).show()
            }
        })
        return "apiservice1"
    }
}