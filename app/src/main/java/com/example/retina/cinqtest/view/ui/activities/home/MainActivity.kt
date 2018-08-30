package com.example.retina.cinqtest.view.ui.activities.home

import android.os.Bundle
import com.example.retina.cinqtest.R
import com.example.retina.cinqtest.data.model.User
import com.example.retina.cinqtest.view.adapter.members.MemberAdapter
import com.example.retina.cinqtest.view.adapter.members.MemberAdapterPresenter
import com.example.retina.cinqtest.view.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(),HomeActivityContract.View {

    private var TABS_NUMBER = 4
    lateinit var presenter: HomeActivityPresenter
    lateinit var memberPresenter: MemberAdapterPresenter
    lateinit var adapter:MemberAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        presenter = HomeActivityPresenter(this)
        presenter.bindViews(this)


    }

    override fun showList(itens: ArrayList<User>) {
        memberPresenter = MemberAdapterPresenter(itens)
        adapter = MemberAdapter(this,memberPresenter)

        list.adapter = adapter

    }

    override fun showWelcome(name: String) {
        welcome.text = "Bem-Vindo, $name"
    }

    override fun toAdd() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toMenuAlbum() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toLogout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toHome() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

