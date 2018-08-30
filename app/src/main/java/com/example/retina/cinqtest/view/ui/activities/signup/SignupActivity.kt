package com.example.retina.cinqtest.view.ui.activities.signup

import android.os.Bundle
import br.com.luan2.lgutilsk.utils.*
import com.example.retina.cinqtest.R
import com.example.retina.cinqtest.data.model.User
import com.example.retina.cinqtest.view.ui.activities.BaseActivity
import com.example.retina.cinqtest.view.ui.activities.home.MainActivity
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : BaseActivity(), SignupActivityContract.View {

    lateinit var presenter: SignupActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        presenter = SignupActivityPresenter(this, this)


        btnSubmit.setOnClickListener { presenter.onSubmit() }
    }


    override fun showError(error: String) {
        onErrorAlert(error)
    }

    override fun checkFields() {
        verifyAndReturn()?.let {
            presenter.onSuccess(it)
            onAlertMessage("Cadastro realizado com sucesso!")
        }
    }

    override fun onSuccess() {
        startActivityClearTask(MainActivity())
    }


    private fun verifyAndReturn(): User? {

        name.guard {
            presenter.onError("Digite um nome válido!")
            name.checkEdittextError("Digite um nome válido!")
            return null
        }

        email.guard(email.isEmailValid()) {
            presenter.onError("Digite um email válido!")
            email.checkEdittextError("Digite um email válido!")
            return null
        }

        password.guard {
            presenter.onError("Digite uma senha!")
            password.checkEdittextError("Digite uma senha!")
            return null
        }


        val user = User()
        user.email = email.getTextString()
        user.password = password.getTextString()
        user.name = name.getTextString()
        return user


    }

}
