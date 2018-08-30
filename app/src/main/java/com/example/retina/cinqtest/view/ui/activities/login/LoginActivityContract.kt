package com.example.retina.cinqtest.view.ui.activities.login

import com.example.retina.cinqtest.data.model.User

interface LoginActivityContract {

     interface Presenter{
        fun onSubmit()
        fun onError(error:String)
        fun onSuccess(user: User)
         fun onSignupClick()
         fun selectFromDb(user: User)
    }


    interface View{
        fun showError(error: String)
        fun onSignUp()
        fun onSuccess()
        fun checkFields()

    }
}