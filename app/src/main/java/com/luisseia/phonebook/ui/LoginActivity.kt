package com.luisseia.phonebook.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.luisseia.phonebook.database.DBhelper
import com.luisseia.phonebook.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBhelper(this)
        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        binding.buttonLogin.setOnClickListener{
            val username =  binding.editEmailLogin.text.toString()
            val password =  binding.editLoginPass.text.toString()
            val logged =  binding.checkBox.isChecked

            if(username.isNotEmpty() && password.isNotEmpty()){
                if(db.login(username, password)){
                    if (logged){
                        val editor : SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("username", username)
                        editor.apply()
                    }
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }else{

                    Toast.makeText(this, "login error", Toast.LENGTH_SHORT)
                }
            }else{

                Toast.makeText(this, "Please isert all required fields", Toast.LENGTH_SHORT)
            }


        }

        binding.textSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.textRecover.setOnClickListener {

        }


    }
}