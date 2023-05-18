package com.example.traning.model.remote

import com.example.traning.resquest.RequestApi
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceAuth {

    @POST("auths/v1/request/")
    suspend fun login(@Body login: RequestApi): Response<String>

}