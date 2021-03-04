package com.simple.simplenotification_2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simple.simplenotification_2.Alarm
import com.simple.simplenotification_2.R
import com.simple.simplenotification_2.ui.adapter.listener.onClickItemListener

class AlarmAdapter(private val aContext: Context, private val listener: onClickItemListener) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    private var alarmData: List<Alarm>? = null

    fun setData(alarmData: ArrayList<Alarm>?) {
        this.alarmData = alarmData
        notifyDataSetChanged()
    }

    fun getData() : List<Alarm>{
        return alarmData!!
    }

    fun clearData() : Boolean {
        (alarmData as ArrayList).clear()
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var root : View?= null

        root = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(root!!)

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val (title, contents) = alarmData!![position]
        if(holder is AlarmViewHolder){
            holder.title.text = title
            holder.contents.text = contents
            holder.itemView.setOnClickListener { v: View? -> listener.onClickItemListener(position, alarmData!![position]) }
        }

    }

    override fun getItemCount(): Int {
        return if (alarmData != null) alarmData!!.size else 0
    }

    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.titleText)
        val contents = itemView.findViewById<TextView>(R.id.contentText)
    }

}
