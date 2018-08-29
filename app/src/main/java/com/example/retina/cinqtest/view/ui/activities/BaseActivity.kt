package com.example.retina.cinqtest.view.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.luan2.lgutilsk.utils.onErrorAlert
import br.com.luan2.lgutilsk.utils.showDebugDBAddressLogToast
import br.com.luan2.lgutilsk.utils.startActivity
import com.example.retina.cinqtest.R
import com.example.retina.cinqtest.data.model.User
import com.example.retina.cinqtest.singleton.Session

open class BaseActivity : AppCompatActivity() {

    var session = Session.shared.also {
        it.context = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDebugDBAddressLogToast()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = when (item.itemId) {
            android.R.id.home -> onBackPressed()
//            R.id.action_edit -> startActivity(EditProfileActivity())
//            R.id.search ->  startActivity(SearchActivity())
//            R.id.edit -> startActivity(EditProfileActivity())
//            R.id.filter -> startActivity(FilterActivity())
            else -> return true

        }
        return super.onOptionsItemSelected(item)
    }


}
