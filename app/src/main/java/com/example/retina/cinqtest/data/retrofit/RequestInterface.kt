package com.example.retina.cinqtest.data.retrofit

import com.example.retina.cinqtest.data.model.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Luan on 17/10/17.
 */
interface RequestInterface {

    @POST("login")
    abstract fun authUser(@Body json: RequestBody): Call<User>

}