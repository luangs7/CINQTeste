package com.example.retina.cinqtest.view.ui.activities.login

import android.content.Context
import br.com.luan2.lgutilsk.utils.toInt
import com.example.retina.cinqtest.data.db.database
import com.example.retina.cinqtest.data.model.User
import com.example.retina.cinqtest.singleton.Session
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.parseOpt
import org.jetbrains.anko.db.select

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

    override fun selectFromDb(user: User) {
        val checkUser = context.database.use {
            val mail = user.email
            select(User.TABLE_NAME)
                    .whereArgs(User.COLUMN_EMAIL + "={email}", "email" to mail as Any)
                    .exec {
                        parseOpt(object : MapRowParser<User> {
                            override fun parseRow(columns: Map<String, Any?>): User {
                                val name = columns.getValue(User.COLUMN_NAME).toString()
                                val id = columns.getValue(User.COLUMN_ID).toString().toInt()
                                val email = columns.getValue(User.COLUMN_EMAIL).toString()
                                val password = columns.getValue(User.COLUMN_PASS).toString()

                                if (password == user.password) {
                                    onSuccess(User(name, email, password, id))

                                } else {
                                    onError("Senha não confere!")
                                }

                                return User(name, email, password, id)

                            }
                        })
                    }
        }


        if (checkUser == null) {
            onError("Dados incorretos!")
        }
    }
}