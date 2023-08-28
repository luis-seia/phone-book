package com.luisseia.phonebook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.luisseia.phonebook.database.DBhelper


import com.luisseia.phonebook.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBhelper(this)

        binding.buttonSignup.setOnClickListener{
            val username = binding.editEmailSign.text.toString()
            val password = binding.editPassSign.text.toString()
            val passwordC = binding.editConfirmPass.text.toString()

           if (validation(username, password, passwordC)){
              val res = db.insertUser(username, password)
               if (res > 0){
                   Toast.makeText(this, "sucess", Toast.LENGTH_SHORT)
                   finish()
               }else{
                   Toast.makeText(this, "error", Toast.LENGTH_SHORT)
                   clearFields()
               }
           }

        }


    }

    fun validation(username: String, pass: String, passC: String): Boolean{
        if(username.isNullOrEmpty() && pass.isNullOrEmpty() && passC.isNullOrEmpty()){
            Toast.makeText(this, "Please isert all required fields", Toast.LENGTH_SHORT)
            return true
        }else{
            if(pass == passC){
                return true
            }else{
                return false
                Toast.makeText(this, "Passswords dont match", Toast.LENGTH_SHORT)
            }
        }
    }

    fun clearFields(){
        binding.editEmailSign.setText("")
        binding.editPassSign.setText("")
        binding.editConfirmPass.setText("")
    }
}