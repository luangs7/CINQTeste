package com.example.retina.cinqtest.singleton

import android.content.Context
import com.example.retina.cinqtest.data.model.User
import org.jetbrains.anko.toast

/**
 * Created by luan silva on 30/05/18.
 */

class Session {
    val spName = "SharedPreferences"

    companion object {
        val shared by lazy {
            Session()
        }
    }

    lateinit var context: Context

    //declare objects or lists who will persists
    var user: User? = null

    var isUserInSession: Boolean = false

    inline fun logout(completion: () -> Unit) {
        user = null
        val settings = context.getSharedPreferences(spName, 0)
        val editor = settings.edit()
        editor.clear()

        if (editor.commit()) {
            completion()
        }
    }

    inline fun needAuth(context: Context, completion: () -> Unit) {
        context.toast("Você precisa estar logado para continuar!")
        completion()
    }
}
