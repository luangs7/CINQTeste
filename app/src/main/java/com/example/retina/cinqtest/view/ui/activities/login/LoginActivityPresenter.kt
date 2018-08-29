package com.example.retina.cinqtest.view.ui.activities.login

import android.content.Context
import com.example.retina.cinqtest.data.model.User
import com.example.retina.cinqtest.singleton.Session

class LoginActivityPresenter:LoginActivityContract.Presenter {

    lateinit var loginActivityView:LoginActivityContract.View
    lateinit var context: Context

    constructor(view:LoginActivityContract.View,context: Context){
        this.loginActivityView = view
        this.context = context
    }

    override fun onSignupClick() {
        loginActivityView.onSignUp()
    }

    override fun onSubmit() {
        loginActivityView.checkFields()
    }

    override fun onError(error: String) {
        loginActivityView.showError(error)
    }

    override fun onSuccess(user: User) {
        Session.shared.user = user
        user.save(context)
        loginActivityView.onSuccess()
    }
}