package com.example.retina.cinqtest.view.adapter.members

import android.content.Context
import com.example.retina.cinqtest.data.db.database
import com.example.retina.cinqtest.data.model.User
import org.jetbrains.anko.db.delete

class MemberAdapterPresenter : MemberAdapterContract.Presenter {


    var itens = ArrayList<User>()
    lateinit var rowView: MemberAdapterContract.View

    constructor(itens: ArrayList<User>) {
        this.itens = itens
    }

    fun initView(view: MemberAdapterContract.View) {
        this.rowView = view
    }

    fun onBindRepositoryRowViewAtPosition(position: Int, viewHolder: MemberAdapter.ViewHolder) {
        val repo = itens[position]
        rowView.bindName(repo.name!!, viewHolder)
        bindListeners(viewHolder, position)
    }

    override fun bindListeners(viewHolder: MemberAdapter.ViewHolder, position: Int) {
        rowView.bindListeners(viewHolder, position)
    }

    fun getRepositoriesRowsCount(): Int {
        return itens.count()
    }

    override fun onEdit(user: User,context: Context) {

    }

    override fun onDelete(user: User,context: Context) {
        context.database.use {
//            delete()
            rowView.deleteSuccess()
        }
    }
}