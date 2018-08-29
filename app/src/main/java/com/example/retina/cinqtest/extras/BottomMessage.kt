package com.example.retina.cinqtest.extras

import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.example.retina.cinqtest.R

/**
 * Created by Luan on 26/01/18.
 */
class BottomMessage(activity: Activity) {

    var activity: Activity? = null
    lateinit var mBottomSheetDialog: Dialog


    init {
        this.activity = activity
    }

    fun setDialog(resource: Int, callback: (View, Dialog) -> Unit) {
        val view = activity!!.layoutInflater.inflate(resource, null)

        mBottomSheetDialog = Dialog(activity, R.style.MaterialDialogSheet)
        mBottomSheetDialog.setContentView(view)
        mBottomSheetDialog.setCancelable(false)
        mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mBottomSheetDialog.window!!.setGravity(Gravity.BOTTOM)
        mBottomSheetDialog.show()

        callback(view, mBottomSheetDialog)

    }

    fun setDialog(resource: Int, size: String, callback: (View, Dialog) -> Unit) {
        val view = activity!!.layoutInflater.inflate(resource, null)

        mBottomSheetDialog = Dialog(activity, R.style.MaterialDialogSheet)
        mBottomSheetDialog.setContentView(view)
        mBottomSheetDialog.setCancelable(false)

        if (size == "wrap")
            mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        else if (size == "match")
            mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        else {
            if (size.toInt() > 400)
                mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, size.toInt())
            else
                mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        }

        mBottomSheetDialog.window!!.setGravity(Gravity.BOTTOM)
        mBottomSheetDialog.show()

        callback(view, mBottomSheetDialog)

    }


}