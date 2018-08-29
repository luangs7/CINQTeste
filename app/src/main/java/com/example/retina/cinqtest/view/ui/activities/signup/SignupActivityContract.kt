package com.example.retina.cinqtest.view.ui.activities.signup

import com.example.retina.cinqtest.data.model.User

interface SignupActivityContract {

    interface Presenter {
        fun onSubmit()
        fun onSuccess(user: User)
        fun onError(error: String)
    }

    interface View {
        fun showError(error: String)
        fun checkFields()
        fun onSuccess()
    }

}