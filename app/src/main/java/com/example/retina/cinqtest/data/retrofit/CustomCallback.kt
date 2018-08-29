package com.example.retina.cinqtest.data.retrofit

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import br.com.luan2.lgutilsk.utils.isConnectedToNetwork
import br.com.luan2.lgutilsk.utils.startActivityClearTask
import com.example.retina.cinqtest.R
import com.example.retina.cinqtest.data.dao.LocalDbImplement
import com.example.retina.cinqtest.data.model.BaseRequest
import com.example.retina.cinqtest.data.model.User
import com.example.retina.cinqtest.extras.BottomMessage
import com.example.retina.cinqtest.extras.createSnackProgress
import com.example.retina.cinqtest.extras.dismissSnackProgress
import com.example.retina.cinqtest.view.ui.activities.MainActivity
import com.google.gson.Gson
import org.jetbrains.anko.find
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Luan on 17/10/17.
 */
@SuppressWarnings("unused")
class CustomCallback<T> : Callback<T> {

    var activity: Activity
    lateinit var context: Context
    lateinit var dialog: AlertDialog
    lateinit var dialogBuilder: AlertDialog.Builder
    lateinit var snackBar: Snackbar
    lateinit var onResponse: OnResponse<T>
    lateinit var viewLayout: View
    val progressText = "Carregando dados..."

    constructor(activity: Activity) {
        this.activity = activity
        this.onResponse = onResponse
    }

    constructor(activity: Activity, onResponse: OnResponse<T>) {
        this.activity = activity
        createProgress(progressText)
        this.onResponse = onResponse
    }

    constructor(activity: Activity, viewLayout: View, onResponse: OnResponse<T>) {
        this.activity = activity
        createProgress(progressText)
        this.onResponse = onResponse
        this.viewLayout = viewLayout
    }

    constructor(activity: Activity, onResponse: OnResponse<T>, loadDialog: Boolean) {
        this.activity = activity
        if (loadDialog) {
            try {
                createProgress(progressText)
            } catch (ex: Throwable) {
                Log.e(" dialog!!", "CustomCallback: ", ex)
            }
        } else {

        }

        this.onResponse = onResponse
    }

    constructor(activity: Activity, Text: String, onResponse: OnResponse<T>) {
        this.activity = activity
        createProgress(Text)
        this.onResponse = onResponse
    }

    constructor(activity: Activity, Text: String, viewLayout: View, onResponse: OnResponse<T>) {
        this.activity = activity
        createProgress(Text)
        this.onResponse = onResponse
        this.viewLayout = viewLayout
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        var error = ""
        try {
//            dialog.dismiss()
            dismissProgress()
        } catch (e: Exception) {

        }

        if (response!!.isSuccessful)
            onResponse.onResponse(response.body())
        else {
            if (response.code() in 204..422) {

                if (response.code() == 204) {
                    onResponse.onResponse(response.body())
                    return
                }

                if (response.code() == 401 && LocalDbImplement<User>(activity).getDefault(User::class.java) != null) {
                    BottomMessage(activity).setDialog(R.layout.alert_bottom) { view: View, _: Dialog ->
                        val title = view.find<TextView>(R.id.title)
                        val content = view.find<TextView>(R.id.text)
                        val positive = view.find<Button>(R.id.positive)

                        title.text = activity.getResources().getString(R.string.error401)
                        content.text = "Infelizmente sua sessão expirou, faça o login novamente."
                        positive.setOnClickListener {
                            LocalDbImplement<User>(activity).clearObject(User::class.java)
                            activity.startActivityClearTask(MainActivity())

                        }

                    }
                    return
                }

                response.errorBody()?.let {
                    error = it.string()
                }
//                onResponse.onFailure(Throwable(BaseRequest.formatJson(error)))
                onResponse.onFailure(Throwable(Gson().fromJson<BaseRequest>(error, BaseRequest::class.java).message))
            } else {
                error = CallbackHandlerError.handleError(response.code(), activity)
                onResponse.onFailure(Throwable(error))
            }
        }
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        val problem: String
        dismissProgress()

        try {
            if (!activity.isConnectedToNetwork)
                problem = activity.resources.getString(R.string.noConnect)
            else
                problem = activity.resources.getString(R.string.errorConnect)

            val viewGroup = (activity.findViewById(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
            val snack = Snackbar
                .make(viewGroup, problem, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .setAction("Tentar novamente?") { onResponse.onRetry(t) }
            snack.show()
        } catch (e: Exception) {
            onResponse.onFailure(t!!)
        }
    }

    interface OnResponse<T> {
        fun onResponse(response: T?)
        fun onFailure(t: Throwable?)
        fun onRetry(t: Throwable?)
    }

    private fun createProgress(message: String) {
        snackBar = activity.createSnackProgress(message)

        snackBar.show()
    }

    private fun dismissProgress() = snackBar.dismissSnackProgress(activity)
}

