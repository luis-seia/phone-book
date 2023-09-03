package com.luisseia.phonebook.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.luisseia.phonebook.R
import com.luisseia.phonebook.database.DBhelper
import com.luisseia.phonebook.databinding.ActivityNewContactBinding

class NewContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewContactBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
     var id : Int? = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = DBhelper(applicationContext)
        val i = intent

        binding.buttonSave.setOnClickListener{
            val name = binding.editName.text.toString()
            val address = binding.adress.text.toString()
            val phone =  binding.editNumber.text.toString().toInt()
            val email = binding.editEmail.text.toString()
            val imageId = 1

            if (name.isNotEmpty() && address.isNotEmpty() && email.isNotEmpty()){
                val res = db.insertContact(name, address, email,phone , imageId )
                if (res>0){
                    Toast.makeText(applicationContext, " Saved", Toast.LENGTH_SHORT)
                    finish()
                    setResult(1, i)
                }else{
                    Toast.makeText(applicationContext, " Insert Error", Toast.LENGTH_SHORT)
                }
            }
        }

        binding.buttonCancel.setOnClickListener{
            setResult(0, i)
            finish()
            Toast.makeText(applicationContext, " Canceled", Toast.LENGTH_SHORT)
        }

        binding.imageView2.setOnClickListener{
            launcher.launch(Intent(applicationContext, ContactIMageSelectionActivity::class.java))
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.data == null && it.resultCode == 1){
                 id = it.data?.extras?.getInt("id")
                binding.imageView2.setImageDrawable(resources.getDrawable(id!!))
            }else{
                id = 1
                binding.imageView2.setImageResource(R.drawable.avatar1)
            }
        }
    }
}