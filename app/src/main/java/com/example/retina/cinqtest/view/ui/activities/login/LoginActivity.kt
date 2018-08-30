package com.example.retina.cinqtest.view.ui.activities.login

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.EditText
import br.com.luan2.lgutilsk.utils.*
import com.example.retina.cinqtest.R
import com.example.retina.cinqtest.R.id.*
import com.example.retina.cinqtest.data.db.database
import com.example.retina.cinqtest.data.model.User
import com.example.retina.cinqtest.view.ui.activities.BaseActivity
import com.example.retina.cinqtest.view.ui.activities.home.MainActivity
import com.example.retina.cinqtest.view.ui.activities.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.db.*

class LoginActivity : BaseActivity(), LoginActivityContract.View {
    private var isResized: Boolean = false


    private lateinit var presenter: LoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginActivityPresenter(this, this)

        //LISTENER

        btnSubmit.setOnClickListener { _: View? ->
            presenter.onSubmit()
        }

        signup.setOnClickListener { presenter.onSignupClick() }


    }

    private fun verifyAndReturn(): User? {
        val fields: ArrayList<EditText> = arrayListOf(login, password)


        if (!checkEmptyMultipleWithError(fields)) {
            if (login.isEmailValid()) {
                val user = User()
                user.email = login.getTextString()
                user.password = password.getTextString()
                return user
            } else {
                login.checkEdittextError("Digite um email válido!")
                presenter.onError("Digite um email valido!")
            }
        } else {
            presenter.onError("Todos os campos são obrigatorios!")

        }
        return null
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        checkKeyboardOpen()
        super.onWindowFocusChanged(hasFocus)
    }

    override fun showError(error: String) {
        onErrorAlert(error)
    }

    override fun onSignUp() {
        startActivity(SignupActivity())
    }

    override fun onSuccess() {
        startActivityClearTask(MainActivity())
    }

    override fun checkFields() {
        verifyAndReturn()?.let {
            presenter.selectFromDb(it)
        }
    }


    private fun checkKeyboardOpen() {

        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            window.decorView.getWindowVisibleDisplayFrame(r)
            val screenHeight = window.decorView.rootView.height

            val keypadHeight = screenHeight - r.bottom

            if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                if (!isResized) {
                    val sy = this.login.scrollY
                    val sh = login.height
                    scrollView.smoothScrollBy(0, sy + sh * 2)
                    isResized = true
                }
            } else {
                isResized = false
            }
        }
    }

}
