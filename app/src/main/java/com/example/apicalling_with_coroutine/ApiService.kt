package com.example.apicalling_with_coroutine

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users/lloyd2205/repos")
    fun getUserData(): Call<ResponseModel>
}