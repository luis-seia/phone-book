package com.luisseia.phonebook.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luisseia.phonebook.R
import com.luisseia.phonebook.databinding.ActivityContactImageSelectionBinding

class ContactIMageSelectionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityContactImageSelectionBinding
    private lateinit var i : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactImageSelectionBinding.inflate(layoutInflater)

        setContentView(binding.root)
        i = intent

        binding.imageView3.setOnClickListener{ sendID(R.drawable.avatar2) }
        binding.imageView4.setOnClickListener{ sendID(R.drawable.avatar3) }
        binding.imageView5.setOnClickListener{ sendID(R.drawable.avatar5) }
        binding.imageView6.setOnClickListener{ sendID(R.drawable.avatar6) }
        binding.imageView7.setOnClickListener{ sendID(R.drawable.avatar7) }
        binding.imageView8.setOnClickListener{ sendID(R.drawable.avatar8) }


    }

    private fun sendID( id : Int ){
        i.putExtra("id", id)
        setResult(1, i)
        finish()
    }
}