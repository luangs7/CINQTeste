package com.example.retina.cinqtest.view.ui.activities.login

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.EditText
import br.com.luan2.lgutilsk.utils.*
import com.amitshekhar.utils.DatabaseHelper.exec
import com.example.retina.cinqtest.R
import com.example.retina.cinqtest.R.id.*
import com.example.retina.cinqtest.data.db.database
import com.example.retina.cinqtest.data.model.User
import com.example.retina.cinqtest.view.ui.activities.BaseActivity
import com.example.retina.cinqtest.view.ui.activities.MainActivity
import com.example.retina.cinqtest.view.ui.activities.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.startActivity

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
            val checkUser = database.use {
                val mail = it.email
                select(User.TABLE_NAME)
                        .whereArgs(User.COLUMN_EMAIL + "={email}", "email" to mail as Any)
                        .exec {
                            parseOpt(object : MapRowParser<User> {
                                override fun parseRow(columns: Map<String, Any?>): User {
                                    val name = columns.getValue(User.COLUMN_NAME).toString()
                                    val id = columns.getValue(User.COLUMN_ID).toString().toInt()
                                    val email = columns.getValue(User.COLUMN_EMAIL).toString()
                                    val password = columns.getValue(User.COLUMN_PASS).toString()

                                    if (password.equals(it.password)) {
                                        presenter.onSuccess(User(name, email, password, id))

                                    } else {
                                        presenter.onError("Senha não confere!")
                                    }

                                    return User(name, email, password, id)

                                }
                            })
                        }
            }


            if (checkUser == null) {
                presenter.onError("Dados incorretos!")
            }
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
