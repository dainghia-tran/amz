package com.myquotes.network

import retrofit2.http.GET
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Headers

interface Service {
    @get:GET("random")
    @get:Headers("X-RapidAPI-Key: 3d3ee360dbmshcd89b7448de2e3ep122b08jsn79f43954289e", "X-RapidAPI-Host: quotes15.p.rapidapi.com")
    val randomQuote: Call<JsonObject?>?
}