//package com.example.retina.cinqtest.view.adapter
//
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ProgressBar
//import android.widget.RelativeLayout
//import android.widget.TextView
//import br.com.luan2.lgutilsk.utils.loadUrl
//import com.example.retina.cinqtest.R
//import com.example.retina.cinqtest.extras.RecyclerViewOnClickListenerHack
//import com.example.retina.cinqtest.extras.RecyclerViewOnLongClickListenerHack
//import com.example.retina.cinqtest.view.ui.activities.setCustomFont
//import com.example.retina.cinqtest.view.ui.activities.setCustomFontRegular
//import com.example.retina.cinqtest.view.ui.activities.work.WorkDetailsActivity
//import de.hdodenhof.circleimageview.CircleImageView
//import java.util.ArrayList
//
//class TatuadoresSearchAdapter : RecyclerView.Adapter<TatuadoresSearchAdapter.ViewHolder> {
//
//    private var objects = ArrayList<Filter>()
//    private var activity: Activity? = null
//    private var inflater: LayoutInflater? = null
//    private var recyclerViewOnClickListenerHack: RecyclerViewOnClickListenerHack? = null
//    private var recyclerViewOnLongClickListenerHack: RecyclerViewOnLongClickListenerHack? = null
//    internal var view: Boolean = false
//    var isLoading = false
//
//    constructor(context: Activity) {
//        this.activity = context
//        this.inflater = LayoutInflater.from(context)
//    }
//
//    constructor(context: Activity, itens: ArrayList<Filter>, view: Boolean) {
//        this.activity = context
//        this.objects = itens
//        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        this.view = view
//    }
//
//    override fun getItemViewType(position: Int): Int =
//        if (position == objects.size) {
//            2
//        } else {
//            1
//        }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TatuadoresSearchAdapter.ViewHolder {
//        if (viewType == 1) {
//            val v = inflater!!.inflate(R.layout.adapter_search_tatuador, viewGroup, false)
//            return ViewHolder(v)
//        } else {
//            val v = inflater!!.inflate(R.layout.item_loader_lista, viewGroup, false)
//            return ViewHolder(v)
//        }
//    }
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
//
//        if (getItemViewType(position) == 2) {
//            prepareLoad(viewHolder)
//        } else {
//            val `object` = objects[position]
//            prepare(viewHolder, `object`, position)
//        }
//
//    fun prepareLoad(viewHolder: TatuadoresSearchAdapter.ViewHolder) =
//        if (isLoading) {
//            viewHolder.loading?.visibility = View.VISIBLE
//            viewHolder.contentProgress?.visibility = View.VISIBLE
//        } else {
//            viewHolder.loading?.visibility = View.GONE
//            viewHolder.contentProgress?.visibility = View.GONE
//        }
//
//    override fun getItemCount(): Int = objects.size + 1
//
//    private fun prepare(holder: ViewHolder, obj: Filter, position: Int) {
//
//        holder.nameProfile!!.setCustomFont(activity!!,"Montserrat-Bold.ttf")
//        holder.nameFantasy!!.setCustomFontRegular(activity!!)
//
//
//        obj.profile_image?.let { holder.imageProfile!!.loadUrl(it) }
//            ?: holder.imageProfile!!.loadUrl("https://via.placeholder.com/180x180")
//        holder.nameProfile!!.text = obj.name?.toUpperCase()
//        holder.nameFantasy!!.text = obj.fantasy_name
//        val adapter = PortifolioPerfilAdapter(activity!!)
//        activity?.let {
//            obj.imagens?.let {
//                val llm = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//                adapter.refresh(it)
//                holder.recyclerList!!.layoutManager = llm
//                holder.recyclerList!!.setHasFixedSize(true)
//                holder.recyclerList!!.adapter = adapter
//                holder.recyclerList!!.visibility = View.VISIBLE
//
//                adapter.setRecyclerViewOnClickListenerHack(object : RecyclerViewOnClickListenerHack {
//                    override fun onClickListener(view: View, position: Int) {}
//
//                    override fun onClickListener(view: View, `object`: Any, position: Int) {
//                        val selected = `object` as Portifolio
//                        val intent = Intent(activity, WorkDetailsActivity::class.java)
//                        selected.type?.let {
//                            intent.putExtra("isAutoral", !it.isPortifolio())
//                        }
//                        intent.putExtra("id", selected.id!!)
//                        activity!!.startActivity(intent)
//                    }
//                })
//            } ?: recyclerGone(holder.recyclerList!!)
//        }
//    }
//
//    fun recyclerGone(list: RecyclerView) {
//        list.visibility = View.GONE
//    }
//
//    fun empty(field: TextView) {
//        field.text = "Indiponível"
//    }
//
//    fun addData(mList: List<Filter>) {
//        isLoading = false
//        this.objects.addAll(mList)
//        notifyDataSetChanged()
//    }
//
//    fun refresh() =
//        notifyDataSetChanged()
//
//    fun refresh(mList: List<Filter>) {
//        this.objects.clear()
//        this.objects.addAll(mList)
//        notifyDataSetChanged()
//    }
//
//    fun setRecyclerViewOnClickListenerHack(r: RecyclerViewOnClickListenerHack) {
//        recyclerViewOnClickListenerHack = r
//    }
//
//    fun setRecyclerViewOnLongClickListenerHack(recyclerViewOnLongClickListenerHack: RecyclerViewOnLongClickListenerHack) {
//        this.recyclerViewOnLongClickListenerHack = recyclerViewOnLongClickListenerHack
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
//        var nameProfile: TextView?
//        var nameFantasy: TextView?
//        var imageProfile: CircleImageView?
//        var recyclerList: RecyclerView?
//        var loading: ProgressBar?
//        var contentProgress: RelativeLayout?
//
//        init {
//            imageProfile = itemView.findViewById(R.id.imageProfile) as CircleImageView?
//            nameProfile = itemView.findViewById(R.id.nameProfile) as TextView?
//            nameFantasy = itemView.findViewById(R.id.nameFantasy) as TextView?
//            recyclerList = itemView.findViewById(R.id.recyclerList) as RecyclerView?
//
//            loading = itemView.findViewById(R.id.loading) as ProgressBar?
//            contentProgress = itemView.findViewById(R.id.contentProgress) as RelativeLayout?
//
//            itemView.setOnClickListener(this)
//            itemView.setOnLongClickListener(this)
//        }
//
//        override fun onClick(v: View) {
//            if (recyclerViewOnClickListenerHack != null) {
//                recyclerViewOnClickListenerHack!!.onClickListener(v, objects[adapterPosition], adapterPosition)
//            }
//        }
//
//        override fun onLongClick(v: View): Boolean {
//            if (recyclerViewOnLongClickListenerHack != null) {
//                recyclerViewOnLongClickListenerHack!!.onLongPressClickListener(v, objects[adapterPosition], adapterPosition)
//            }
//            return true
//        }
//    }
//}
//
