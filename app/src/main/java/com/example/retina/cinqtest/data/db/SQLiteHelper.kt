package com.example.retina.cinqtest.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.amitshekhar.utils.DatabaseHelper
import com.example.retina.cinqtest.data.model.User
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.PRIMARY_KEY
import org.jetbrains.anko.db.*

class SQLiteHelper (ctx: Context) : ManagedSQLiteOpenHelper(ctx, "LibraryDatabase", null, 1) {
    companion object {
        private var instance: SQLiteHelper? = null

        @Synchronized
        fun Instance(context: Context): SQLiteHelper {
            if (instance == null) {
                instance = SQLiteHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(database: SQLiteDatabase) {
        database.createTable(User.TABLE_NAME, true, User.COLUMN_ID to INTEGER + PRIMARY_KEY, User.COLUMN_NAME to TEXT, User.COLUMN_EMAIL to TEXT, User.COLUMN_PASS to TEXT)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.dropTable(User.TABLE_NAME, true)
    }
}

val Context.database: SQLiteHelper
    get() = SQLiteHelper.Instance(applicationContext)