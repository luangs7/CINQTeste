package com.example.retina.cinqtest.data.retrofit

import android.content.Context
import com.example.retina.cinqtest.extras.UnitConverterFactory
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Luan on 17/10/17.
 */
class ApiManager {

    lateinit var context: Context
    var retrofit: Retrofit
    var okHttpClient: OkHttpClient


    constructor(context: Context, endpoint: String? = "https://ninky.herokuapp.com/api/v1/") {

        this.context = context

        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY


        okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
                .readTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
                .writeTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .build()


        val gson = GsonBuilder()
                .create()


        retrofit = Retrofit.Builder()
                .baseUrl(endpoint)
                .client(okHttpClient)
                .addConverterFactory(UnitConverterFactory)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

    }


    constructor(retrofit: Retrofit, okHttpClient: OkHttpClient) {
        this.retrofit = retrofit
        this.okHttpClient = okHttpClient
    }


    constructor(context: Context, retrofit: Retrofit, okHttpClient: OkHttpClient) {
        this.context = context
        this.retrofit = retrofit
        this.okHttpClient = okHttpClient
    }


}