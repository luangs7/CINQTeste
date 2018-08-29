package com.example.retina.cinqtest.data.retrofit

import android.app.Activity
import com.example.retina.cinqtest.R

/**
 * Created by squarebits on 01/06/18.
 */
class CallbackHandlerError {

    companion object {
        fun handleError(code: Int, activity: Activity): String {
            when (code) {
                401 -> return activity.getResources().getString(R.string.error401)
                403 -> return activity.getResources().getString(R.string.error401)
                500 -> return activity.getResources().getString(R.string.error500)
                503 -> return activity.getResources().getString(R.string.error503)
                else -> return activity.getResources().getString(R.string.errorNoCode)
            }
        }
    }


}