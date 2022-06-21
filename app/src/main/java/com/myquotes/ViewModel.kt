package com.myquotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.myquotes.network.Quote
import com.myquotes.network.Service
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ViewModel: ViewModel() {
    val quote:MutableLiveData<Quote> = MutableLiveData(Quote())

    init {
        random()
    }

    fun random(){
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.quotable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val randomQuoteService = retrofit.create<Service>();

        randomQuoteService.randomQuote?.enqueue(object : Callback<JsonObject?> {
            override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {
                quote.value = Gson().fromJson(response.body(), Quote::class.java)
            }

            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                t.printStackTrace()
            }
        });
    }

}