package com.myquotes

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myquotes.databinding.ActivityMainBinding
import com.myquotes.network.Quote


class MainActivity : AppCompatActivity() {
    var isShowMenu:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val viewModel = ViewModelProvider(this).get(ViewModel::class.java);
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("my_quotes",Context.MODE_PRIVATE) ?: return

        viewModel.quote.observe(this) {
            binding.tvQuote.text = it.content
            binding.tvAuthor.text = it.author

            val tagAdapter = TagAdapter(it.tags)

            binding.rv.adapter = tagAdapter
        }

        binding.cv.setOnClickListener{
            viewModel.random()

            val color = Utils.randomLightColor()
            binding.clLayout.background = ColorDrawable(color)
        }

        binding.fab.setOnClickListener{
            binding.fabMenu.visibility = if(isShowMenu) View.INVISIBLE else View.VISIBLE
            isShowMenu = !isShowMenu
        }

        binding.cvSave.setOnClickListener{
            val savedData = sharedPref.getString("quotes", "")
            val type = object: TypeToken<MutableList<Quote>>(){}.type
            val savedQuotes = Gson().fromJson<MutableList<Quote>>(savedData, type) ?:ArrayList()
            viewModel.quote.value?.let { it1 ->
                if(!savedQuotes.contains(it1)){
                    savedQuotes.add(it1)
                    val a = Gson().toJson(savedQuotes)
                    with(sharedPref.edit()) {
                        putString("quotes", a)
                        apply()
                    }
                }
            }
        }

        binding.cvSaved.setOnClickListener{
            val intent = Intent(this, SavedQuotesActivity::class.java)
            startActivity(intent)
        }

        binding.cvDonate.setOnClickListener{
            val intent = Intent(this, PayfortActivity::class.java)
            startActivity(intent)
        }
    }
}