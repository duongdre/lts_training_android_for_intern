package com.example.traning.model.remote

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("users")
    fun fetchAllUsers(): Call<List<String>>
}