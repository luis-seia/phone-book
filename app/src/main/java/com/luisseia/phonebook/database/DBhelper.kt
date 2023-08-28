package com.luisseia.phonebook.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.luisseia.phonebook.model.User

class DBhelper(context :Context) : SQLiteOpenHelper(context, "database.db", null, 1) {
 val sql = arrayOf(
  "CREATE TABLE users ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)"
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
}


