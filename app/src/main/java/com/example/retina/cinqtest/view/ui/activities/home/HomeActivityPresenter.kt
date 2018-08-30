package com.example.retina.cinqtest.view.ui.activities.home

import android.content.Context
import com.example.retina.cinqtest.R.id.list
import com.example.retina.cinqtest.data.db.database
import com.example.retina.cinqtest.data.model.User
import org.jetbrains.anko.db.*

class HomeActivityPresenter(val viewPresenter: HomeActivityContract.View):HomeActivityContract.Presenter {


    override fun onAdd() {
        viewPresenter.toAdd()
    }

    override fun onMenuAlbum() {
        viewPresenter.toMenuAlbum()
    }

    override fun onLogout() {
        viewPresenter.toLogout()
    }

    override fun onHome() {
        viewPresenter.toHome()
    }

    override fun bindViews(context: Context) {
        viewPresenter.showWelcome(User.get(context)?.name!!)
        val list = ArrayList<User>()
         context.database.use {
            select(User.TABLE_NAME).exec {
                parseList(object : MapRowParser<List<User>>{
                    override fun parseRow(columns: Map<String, Any?>): List<User> {
                        val name = columns.getValue(User.COLUMN_NAME).toString()
                        val id = columns.getValue(User.COLUMN_ID).toString().toInt()

                        list.add(User(name = name,id = id))

                        return list
                    }
                })
            } }





        viewPresenter.showList(list.toCollection(ArrayList()))
    }
}