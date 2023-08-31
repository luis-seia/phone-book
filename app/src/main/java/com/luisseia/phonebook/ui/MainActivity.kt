package com.luisseia.phonebook.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.GeneratedAdapter
import com.luisseia.phonebook.R
import com.luisseia.phonebook.database.DBhelper
import com.luisseia.phonebook.databinding.ActivityMainBinding
import com.luisseia.phonebook.model.Contact

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var contactList : ArrayList<Contact>
    private lateinit var adapter: ArrayAdapter<Contact>
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var dBhelper : DBhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         dBhelper = DBhelper(this)
        val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)
        loadList()
        binding.buttonLogout.setOnClickListener {
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("username", "")
            editor.apply()
            finish()
        }


        binding.buttonAdd.setOnClickListener{
                result.launch(Intent(applicationContext, NewContactActivity::class.java))
        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.data != null && it.resultCode == 1){
                loadList()
            }else if(it.data != null && it.resultCode == 0) {
                Toast.makeText(applicationContext, "Operation canceled", Toast.LENGTH_SHORT)
            }
        }


        binding.list.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, ContactDetailActivity::class.java)
            intent.putExtra("id", contactList[i].id)
            result.launch(intent)
        }
    }

    fun loadList(){

        contactList =  dBhelper.getAllContact()

        adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, contactList)

        binding.list.adapter = adapter
    }
}