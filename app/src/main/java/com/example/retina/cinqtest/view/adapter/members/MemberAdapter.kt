package com.example.retina.cinqtest.view.adapter.members

import android.content.Context
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.retina.cinqtest.R
import org.jetbrains.anko.alert

class MemberAdapter(val context: Context, val presenter: MemberAdapterPresenter) : BaseAdapter(), MemberAdapterContract.View {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    init {
        presenter.initView(this)
    }

    override fun getView(position: Int, mConvertView: View?, parent: ViewGroup?): View {

        var convertView = mConvertView
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_members, null)
            convertView!!.tag = ViewHolder(convertView)
        }

        presenter.onBindRepositoryRowViewAtPosition(position, convertView.tag as ViewHolder)

        return convertView

    }

    override fun getItem(position: Int): Any = presenter.itens[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = presenter.getRepositoriesRowsCount()


    override fun bindName(name: String, viewHolder: ViewHolder) {
        viewHolder.name.text = name
    }

    override fun bindListeners(viewHolder: ViewHolder, position: Int) {
        viewHolder.edit.setOnClickListener { presenter.onEdit(presenter.itens[position], context) }
        viewHolder.delete.setOnClickListener {
            presenter.onDelete(presenter.itens[position], context)
        }
    }

    override fun deleteSuccess() {
        context.alert {
            title = "Sucesso!"
            message = "O usuário foi deletado com sucesso."
        }
        notifyDataSetChanged()
    }

    class ViewHolder(rootView: View) {
        lateinit var name: TextView
        lateinit var edit: ImageButton
        lateinit var delete: ImageButton


        init {
            initView(rootView)
        }

        private fun initView(rootView: View) {
            name = rootView.findViewById(R.id.name) as TextView
            edit = rootView.findViewById(R.id.edit) as ImageButton
            delete = rootView.findViewById(R.id.delete) as ImageButton

        }
    }


}