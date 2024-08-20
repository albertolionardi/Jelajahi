package com.example.jelajahi

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    fun login(@Body loginRequest: LoginRequest): Call<User>
}

data class LoginRequest(
    val username: String,
    val password: String
)