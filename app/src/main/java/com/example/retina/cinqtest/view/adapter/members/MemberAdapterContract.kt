package com.example.retina.cinqtest.view.adapter.members

import android.content.Context
import android.view.View
import com.example.retina.cinqtest.data.model.User

interface MemberAdapterContract {

    interface Presenter{
        fun onEdit(user: User,context: Context)
        fun onDelete(user: User,context: Context)
        fun bindListeners(viewHolder:MemberAdapter.ViewHolder,position: Int)
    }

    interface View{
        fun bindName(name:String, viewHolder: MemberAdapter.ViewHolder)
        fun bindListeners(viewHolder:MemberAdapter.ViewHolder,position:Int)
        fun deleteSuccess()

    }



}