package com.example.retina.cinqtest.view.ui.activities.signup

import android.content.Context
import com.example.retina.cinqtest.data.db.database
import com.example.retina.cinqtest.data.model.User
import com.example.retina.cinqtest.singleton.Session
import org.jetbrains.anko.db.insert
import java.util.*

class SignupActivityPresenter : SignupActivityContract.Presenter {

    lateinit var view: SignupActivityContract.View
    lateinit var context: Context

    constructor(context: Context, view: SignupActivityContract.View) {
        this.context = context
        this.view = view
    }


    override fun onSubmit() {
        view.checkFields()
    }

    override fun onSuccess(user: User) {
        context.database.use {
            insert(User.TABLE_NAME, User.COLUMN_ID to (0 until 100).random().toString(), User.COLUMN_NAME to user.name, User.COLUMN_EMAIL to user.email, User.COLUMN_PASS to user.password)
        }

        Session.shared.user = user
        user.save(context)
        view.onSuccess()

    }

    override fun onError(error: String) {
        view.showError(error)
    }
}

fun ClosedRange<Int>.random() =
        Random().nextInt((endInclusive + 1) - start) + start