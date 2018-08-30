package com.example.retina.cinqtest.view.ui.activities.home

import android.content.Context
import com.example.retina.cinqtest.data.model.User

interface HomeActivityContract {

    interface Presenter{
        fun onAdd()
        fun onMenuAlbum()
        fun onLogout()
        fun onHome()
        fun bindViews(context: Context)
    }

    interface View{
        fun showList(itens:ArrayList<User>)
        fun showWelcome(name:String)
        fun toAdd()
        fun toMenuAlbum()
        fun toLogout()
        fun toHome()
    }

}