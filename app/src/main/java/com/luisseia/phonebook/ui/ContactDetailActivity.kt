package com.luisseia.phonebook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.luisseia.phonebook.R
import com.luisseia.phonebook.database.DBhelper
import com.luisseia.phonebook.databinding.ActivityContactDetailBinding
import com.luisseia.phonebook.model.Contact

class ContactDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactDetailBinding
    private lateinit var db : DBhelper
    private lateinit var contact : Contact
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.extras?.getInt("id")
        db = DBhelper(applicationContext)

        if (id != null) {
           contact = db.getContact( id )
            binding.adress.setText( contact.adress )
            binding.editName.setText( contact.name )
            binding.editEmail.setText( contact.email )
            binding.editNumber.setText( contact.phone.toString() )

        }

        binding.buttonSave.setOnClickListener{
            val name = binding.editName.text.toString()
            val address = binding.adress.text.toString()
            val phone =  binding.editNumber.text.toString().toInt()
            val email = binding.editEmail.text.toString()
            val imageId = 1

           val res = db.updateContact( contact.id, address, name, email, phone, imageId )
            if (res>0){
                Toast.makeText(applicationContext, " Saved", Toast.LENGTH_SHORT)
                finish()
                setResult(1, i)
            }else{
                Toast.makeText(applicationContext, " Insert Error", Toast.LENGTH_SHORT)
            }
        }

        binding.buttonDelete.setOnClickListener{
          val res = db.deleteContact(contact.id)
            if (res>0){
                Toast.makeText(applicationContext, " Deleted", Toast.LENGTH_SHORT)
                finish()
                setResult(1, i)
            }else{
                Toast.makeText(applicationContext, " Delete Error", Toast.LENGTH_SHORT)
                finish()
                setResult(0, i)
            }
        }
    }
}