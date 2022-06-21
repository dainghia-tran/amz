package com.myquotes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myquotes.databinding.ActivitySavedQuotesBinding
import com.myquotes.network.Quote

class SavedQuotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySavedQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = applicationContext.getSharedPreferences("my_quotes", Context.MODE_PRIVATE)
        val savedData = sharedPref.getString("quotes", "")
        val type = object:TypeToken<List<Quote>>(){}.type
        val savedQuotes = Gson().fromJson<List<Quote>>(savedData, type)

        val adapter = SavedQuotesAdapter(savedQuotes)

        binding.rv.adapter = adapter
    }
}