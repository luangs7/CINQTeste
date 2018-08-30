package com.example.retina.cinqtest.view.ui.activities

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import android.widget.Toast
import br.com.luan2.lgutilsk.utils.hideUI
import br.com.luan2.lgutilsk.utils.showDebugDBAddressLogToast
import br.com.luan2.lgutilsk.utils.splashOpen
import br.com.luan2.lgutilsk.utils.startActivityClearTask
import com.example.retina.cinqtest.R
import com.example.retina.cinqtest.data.db.database
import com.example.retina.cinqtest.data.model.User
import com.example.retina.cinqtest.extras.BottomMessage
import com.example.retina.cinqtest.view.ui.activities.login.LoginActivity
import org.jetbrains.anko.find


class SplashActivity : BaseActivity() {

    private val REQUEST_PERMISSIONS_CODE = 128

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        hideUI()
        showDebugDBAddressLogToast()

        session.user = User.get(this)


        splashOpen {
            startActivityClearTask(LoginActivity())
        }

//        session.user?.let {
//            splashOpen {
//                startActivityClearTask(LoginActivity())
//            }
//        }
    }


}
