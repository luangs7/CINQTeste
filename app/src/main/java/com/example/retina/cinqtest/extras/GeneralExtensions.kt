package com.example.retina.cinqtest.extras

import android.R
import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import br.com.luan2.lgutilsk.extras.mask.MyMaskEditText
import br.com.luan2.lgutilsk.utils.appVersionName
import br.com.luan2.lgutilsk.utils.getTextString
import br.com.luan2.lgutilsk.utils.isEmpty
import br.com.luan2.lgutilsk.utils.then
import br.com.luan2.lgutilsk.utils.userInteraction
import com.example.retina.cinqtest.data.model.User
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * Created by squarebits on 18/06/18.
 */

//    println(condition then "true" ?: "false")

fun Boolean.toInt(): Int = this then 1 ?: 0

fun Boolean.stringValue(): String = this then "1" ?: "0"

inline infix fun <reified T> T?.guard(call: () -> Unit): T? {
    if (this is T) return this
    else {
        call()
        return null
    }
}

inline infix fun EditText.guard(call: () -> Unit): String? {
    if (!this.isEmpty()) return getTextString()
    else {
        call()
        return null
    }
}

inline fun EditText.guard(rule: Boolean, call: () -> Unit): String? {
    if (!this.isEmpty() && rule) return getTextString()
    else {
        call()
        return null
    }
}

fun Context.onToast(message: String, completion: () -> Unit) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    Handler().postDelayed({
        completion()
    }, 3500)
}


fun Any.toJson(): RequestBody =
    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Gson().toJson(this))

fun JsonObject.toJson(): RequestBody =
    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Gson().toJson(this))

fun JSONObject.toJson(): RequestBody =
    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Gson().toJson(this))

fun Activity.createSnackProgress(message: String): Snackbar {
    userInteraction(true)

    val viewGroup = (findViewById<ViewGroup>(android.R.id.content)).getChildAt(0) as ViewGroup
    val snackBar = Snackbar.make(viewGroup, message, Snackbar.LENGTH_INDEFINITE)

    //progress bar
    val item = ProgressBar(this, null, R.attr.progressBarStyleLarge)
    item.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)

    val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT).also { it.addRule(RelativeLayout.CENTER_IN_PARENT) }

    val contentLay = snackBar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text).parent as ViewGroup
    contentLay.addView(item, params)

    return snackBar
}

fun Snackbar.dismissSnackProgress(activity: Activity) {
    activity.userInteraction(false)
    dismiss()
}

val Activity.hasNavigationBar: Boolean
    get() {
        if (Build.VERSION.SDK_INT < 19) return false
        return !ViewConfiguration.get(this).hasPermanentMenuKey()
    }

fun Activity.isPortrait() = resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE
fun Activity.isLandscape() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

/**
 * Return true if navigation bar is at the bottom, false otherwise
 */
val Activity.isNavigationBarHorizontal: Boolean
    get() {
        if (!hasNavigationBar) return false
        val dm = resources.displayMetrics
        return !navigationBarCanChangeItsPosition || dm.widthPixels < dm.heightPixels
    }

/**
 * Return true if navigation bar change its position when device rotates, false otherwise
 */
val Activity.navigationBarCanChangeItsPosition: Boolean // Only phone between 0-599dp can
    get() {
        val dm = resources.displayMetrics
        return dm.widthPixels != dm.heightPixels && resources.configuration.smallestScreenWidthDp < 600
    }

val Activity.navigationBarHeight: Int
    get() {
        if (!hasNavigationBar) return 0
        if (navigationBarCanChangeItsPosition && !isPortrait()) return 0
        val idString = if (isPortrait()) "navigation_bar_height" else "navigation_bar_height_landscape"
        val id = resources.getIdentifier(idString, "dimen", "android")
        if (id > 0) return resources.getDimensionPixelSize(id)
        return 0
    }

fun Activity.hasNavBar(resources: Resources): Boolean {
    val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
    return id > 0 && resources.getBoolean(id)
}

fun EditText.myCustomMask(mask: String) {
    addTextChangedListener(MyMaskEditText(this, mask))
}

fun Activity.sendNotification(messageBody: String?, title: String? = this.appVersionName(), pendingIntentActivity: Activity) {

    val intent = Intent(this, pendingIntentActivity::class.java)


    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
        PendingIntent.FLAG_ONE_SHOT)

    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(this)
        .setSmallIcon(com.example.retina.cinqtest.R.mipmap.ic_launcher)
        .setColor(resources.getColor(com.example.retina.cinqtest.R.color.colorPrimary))
        .setContentTitle(title)
        .setContentText(messageBody)
        .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent)
        .setOngoing(false)

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
}

fun ImageView.loadUrlAndGetBitmap(url: String, completion: (Bitmap?) -> Unit) {

    Picasso.with(context).load(url).into(object : Target {
        override fun onBitmapFailed(errorDrawable: Drawable?) {
            completion(null)
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            bitmap?.let {
                this@loadUrlAndGetBitmap.setImageBitmap(it)
                completion(it)
            } ?: completion(null)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        }
    })
}

// Method to save an bitmap to a file
fun Bitmap.bitmapToFile(context: Context): Uri {
    // Get the context wrapper
    val wrapper = ContextWrapper(context)

    // Initialize a new file instance to save bitmap object
    var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
    file = File(file, "donwload-image.jpg")

    try {
        // Compress the bitmap and save in jpg format
        val stream: OutputStream = FileOutputStream(file)
        this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    // Return the saved bitmap uri
    return Uri.parse(file.absolutePath)
}


