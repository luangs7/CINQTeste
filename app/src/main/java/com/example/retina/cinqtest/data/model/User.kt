package com.example.retina.cinqtest.data.model

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.example.retina.cinqtest.data.dao.LocalDbImplement
import com.google.gson.annotations.SerializedName

class User(var name:String? = "", var email:String? = "", var password:String? ="", var id:Int? = 0)
{



    companion object {
        fun get(context: Context): User? = LocalDbImplement<User>(context).getDefault(User::class.java)

        val COLUMN_ID = "id"
        val TABLE_NAME = "Users"
        val COLUMN_NAME = "nome"
        val COLUMN_PASS = "senha"
        val COLUMN_EMAIL = "email"
    }


    fun save(context: Context) = LocalDbImplement<User>(context).save(this)

    fun clear(context: Context) = LocalDbImplement<User>(context).clearObject(User::class.java)

}
