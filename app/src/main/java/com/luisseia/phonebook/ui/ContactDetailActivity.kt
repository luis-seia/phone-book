package com.luisseia.phonebook.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.luisseia.phonebook.R
import com.luisseia.phonebook.database.DBhelper
import com.luisseia.phonebook.databinding.ActivityContactDetailBinding
import com.luisseia.phonebook.model.Contact

class ContactDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactDetailBinding
    private lateinit var db : DBhelper
    private lateinit var contact : Contact
    private lateinit var launcher: ActivityResultLauncher<Intent>
    var imageId : Int? = -1
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.extras?.getInt("id")
        db = DBhelper(applicationContext)
        if (id != null) {
            contact = db.getContact(id)
            populate()
        }else{
            finish()
        }

        binding.buttonEdit.setOnClickListener{
            binding.layoutEditback.visibility = View.GONE
            binding.layoutEditDelete.visibility =  View.VISIBLE
            changeEditText(true)
        }

        binding.btSave.setOnClickListener{
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

        binding.btDelete.setOnClickListener{
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

        binding.btCancel.setOnClickListener {
            populate()
            changeEditText(false)
            binding.layoutEditback.visibility = View.VISIBLE
            binding.layoutEditDelete.visibility =  View.GONE
        }

        binding.buttonBack.setOnClickListener {
            setResult(0, i)
            finish()
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.data != null && it.resultCode == 1){
                imageId = it.data?.extras?.getInt("id")
                binding.imageView9.setImageDrawable(resources.getDrawable(id!!))
            }else{
                imageId = -1
                binding.imageView9.setImageResource(R.drawable.avatar1)
            }
        }
    }

    private fun  populate() {

            binding.adress.setText( contact.adress )
            binding.editName.setText( contact.name )
            binding.editEmail.setText( contact.email )
            binding.editNumber.setText( contact.phone.toString() )
            if (contact.id > 0){
                binding.imageView9.setImageDrawable(resources.getDrawable(contact.imageId))
            }else{
                binding.imageView9.setImageResource(R.drawable.avatar1)
            }
    }

    private fun changeEditText(status: Boolean) {
            binding.editEmail.isEnabled = status
            binding.editName.isEnabled = status
        binding.editNumber.isEnabled = status
        binding.adress.isEnabled = status
    }
}