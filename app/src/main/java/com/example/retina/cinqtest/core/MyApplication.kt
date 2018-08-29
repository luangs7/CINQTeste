package com.example.retina.cinqtest.core

import android.app.Application
import android.content.Context
import android.util.Log


/**
 * Created by luan on 1/15/15.
 */
class MyApplication : Application() {

    val context: Context
        get() = this
    lateinit private var sInstance: MyApplication

    override fun onCreate() {
        //        MultiDex.install(getApplicationContext());
        super.onCreate()
        sInstance = this


    }
}
