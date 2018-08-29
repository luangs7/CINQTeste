//package com.example.retina.cinqtest.view.adapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//import android.widget.ImageView
//import android.widget.RelativeLayout
//import android.widget.TextView
//import com.example.retina.cinqtest.R
//
//class CustomSpinnerAdapter(private val context: Context) : BaseAdapter() {
//
//    private val objects = ArrayList<Stylesheet>()
//    private var selecteds = ArrayList<Stylesheet>()
//    private val layoutInflater: LayoutInflater
//
//    init {
//        this.layoutInflater = LayoutInflater.from(context)
//        selecteds = objects.filter { it.isSelected }.toCollection(ArrayList())
//
//    }
//
//
//    override fun getCount(): Int =
//            objects.size
//
//
//    override fun getItem(position: Int): Stylesheet =
//            objects[position]
//
//
//    override fun getItemId(position: Int): Long =
//            position.toLong()
//
//
//    override fun getView(position: Int, mConvertView: View?, parent: ViewGroup): View {
//        var convertView = mConvertView
//        if (convertView == null) {
//            convertView = layoutInflater.inflate(R.layout.adapter_spinner_styles, null)
//            convertView!!.tag = ViewHolder(convertView)
//        }
//
//        initializeViews(getItem(position), convertView.tag as ViewHolder, position)
//
//        return convertView
//    }
//
//    private fun initializeViews(`object`: Stylesheet, holder: ViewHolder, position: Int) {
//        holder.name.text = `object`.nomeEstilo
//        if (`object`.isSelected) {
//            holder.isCheck.visibility = View.VISIBLE
//            if (!selecteds.contains(`object`))
//                selecteds.add(`object`)
//        } else {
//            holder.isCheck.visibility = View.GONE
//            if (selecteds.contains(`object`))
//                selecteds.remove(`object`)
//        }
//
//        holder.content.setOnClickListener { _: View? ->
//            `object`.isSelected = !`object`.isSelected
//            notifyDataSetChanged()
//        }
//
//    }
//
//
//    fun getSelecteds(): List<Stylesheet> = selecteds
//
//    fun getSelectedsId(): ArrayList<Int> {
//        val ids = ArrayList<Int>()
//
//        for (selected in selecteds) {
//            ids.add(selected.id!!)
//        }
//
//        return ids
//    }
//
//
//    fun refresh() =
//            notifyDataSetChanged()
//
//
//    fun refresh(mList: List<Stylesheet>) {
//        this.objects.clear()
//        this.objects.addAll(mList)
//        selecteds = objects.filter { it.isSelected }.toCollection(ArrayList())
//
//        notifyDataSetChanged()
//    }
//
//
//    internal inner class ViewHolder(rootView: View) {
//        lateinit var name: TextView
//        lateinit var isCheck: ImageView
//        lateinit var content: RelativeLayout
//
//
//        init {
//            initView(rootView)
//        }
//
//        private fun initView(rootView: View) {
//            name = rootView.findViewById(R.id.name) as TextView
//            isCheck = rootView.findViewById(R.id.isCheck) as ImageView
//            content = rootView.findViewById(R.id.content) as RelativeLayout
//
//        }
//    }
//}