package com.luisseia.phonebook.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.luisseia.phonebook.model.Contact
import com.luisseia.phonebook.model.User

class DBhelper(context :Context) : SQLiteOpenHelper(context, "database.db", null, 1) {
 val sql = arrayOf(
  "CREATE TABLE users  ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)",
  "CREATE TABLE contact  (id INTEGER PRIMARY KEY AUTOINCREMENT, address TEXT, name TEXT, email TEXT, phone TEXT, imageID INT)"
 )

 override fun onCreate(p0: SQLiteDatabase) {
  sql.forEach {
   p0.execSQL(it)
  }
 }

 override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

 }


  fun insertUser(username: String, password: String): Long {
      val db = this.writableDatabase
      val contentValues = ContentValues()
      contentValues.put("username", username)
      contentValues.put("password", password)
      val res =  db.insert("users", null, contentValues)
      db.close()
      return res

  }

 fun updateUser(id: Int, username: String, password: String): Int {
  val db = this.writableDatabase
  val contentValues = ContentValues()
  contentValues.put("username", username)
  contentValues.put("password", password)
  val res =  db.update("users", contentValues, "id=?", arrayOf(id.toString()))
  db.close()
  return res
 }

 fun deleteUser(id: Int): Int {
  val db = this.writableDatabase


  val res =  db.delete("users",  "id=?", arrayOf(id.toString()))
  db.close()
  return res
 }

 fun getUser(username: String, password: String): User{
  val db = this.readableDatabase
  val query = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", arrayOf(username, password))

  var user = User()
  if(query.count == 1){
    query.moveToFirst()
     val idIndex =  query.getColumnIndex("id")
     val usernameIndex = query.getColumnIndex("username")
     val passwordIndex = query.getColumnIndex("password")

     user = User(
      id = query.getInt(idIndex),
      username = query.getString(usernameIndex),
      password = query.getString(passwordIndex)
     )
  }
  db.close()
  return user
 }

 fun login(username: String, password: String): Boolean{
  val db = this.readableDatabase
  val query = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", arrayOf(username, password))

  var user = User()
  if(query.count == 1){
   db.close()
  return true
  }else{
   db.close()
   return false
  }

 }

 // CRUD CONTACT

 fun insertContact ( name : String, address : String, email : String, phone : Int, imageId : Int) : Long {
  val db = this.writableDatabase
  val contentValues = ContentValues()
  contentValues.put("name", name)
  contentValues.put("email", email)
  contentValues.put("address", address)
  contentValues.put("phone", phone)
  contentValues.put("imageId", imageId)
  val res =  db.insert("contact", null, contentValues)
  db.close()
  return res

 }

 fun updateContact(id : Int, address : String, name : String, email : String, phone : Int, imageId : Int): Int {
  val db = this.writableDatabase
  val contentValues = ContentValues()
  contentValues.put("name", name)
  contentValues.put("email", email)
  contentValues.put("adress", address)
  contentValues.put("phone", phone)
  contentValues.put("imageId", imageId)
  val res =  db.update("contact", contentValues, "id=?", arrayOf(id.toString()))
  db.close()
  return res
 }

 fun deleteContact(id: Int): Int {
  val db = this.writableDatabase


  val res =  db.delete("contact",  "id=?", arrayOf(id.toString()))
  db.close()
  return res
 }

 fun getContact(id: Int): Contact {
  val db = this.readableDatabase
  val query = db.rawQuery("SELECT * FROM contact WHERE id=?", arrayOf(id.toString()))

  var contact = Contact()
  if(query.count == 1){
   query.moveToFirst()
   val idIndex =  query.getColumnIndex("id")
   val nameIndex = query.getColumnIndex("name")
   val emailIndex = query.getColumnIndex("email")
   val addressIndex = query.getColumnIndex("address")
  val phoneIndex =  query.getColumnIndex("phone")
  val imagedIdIndex = query.getColumnIndex("imageId")


   contact  = Contact(
    id = query.getInt(idIndex),
    name = query.getString(nameIndex),
    adress = query.getString(addressIndex),
    email = query.getString(emailIndex),
    phone =  query.getLong(phoneIndex),
    imageId = 8
   )
  }
  db.close()
  return contact
 }

 fun getAllContact() : ArrayList<Contact>{
  val db = this.readableDatabase
  val query = db.rawQuery("SELECT * FROM  contact", null)
  var listContact = ArrayList<Contact>()
  var contact = Contact()
  if(query.count > 0 ){
   query.moveToFirst()
   val idIndex =  query.getColumnIndex("id")
   val nameIndex = query.getColumnIndex("name")
   val addressIndex = query.getColumnIndex("address")
   val emailIndex = query.getColumnIndex("email")
   val phoneIndex =  query.getColumnIndex("phone")
   val imagedIdIndex = query.getColumnIndex("imageId")

   do {
    contact  = Contact(
     id = query.getInt(idIndex),
     name = query.getString(nameIndex),
     adress = query.getString(addressIndex),
     email = query.getString(emailIndex),
     phone =  query.getLong(phoneIndex),
     imageId = 2
    )
    listContact.add(contact)
   } while (query.moveToNext())

  }
  db.close()
  return listContact
 }

}


