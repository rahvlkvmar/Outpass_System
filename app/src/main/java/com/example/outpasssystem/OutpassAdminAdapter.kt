package com.example.outpasssystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.w3c.dom.Text

class OutpassAdminAdapter(private val OutpassList: ArrayList<AdminOutpassRV>): RecyclerView.Adapter<OutpassAdminAdapter.OutpassViewHolder>(){

    class OutpassViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tv_Name : TextView = itemView.findViewById(R.id.tv_name_rv)
        val tv_rollnum: TextView = itemView.findViewById(R.id.tv_rollnum_rv)
        val tv_leave: TextView = itemView.findViewById(R.id.tv_leave_rv)
        val tv_arrive: TextView = itemView.findViewById(R.id.tv_arrive_rv)
        val tv_transport: TextView = itemView.findViewById(R.id.tv_transport_rv)
        val tv_purpose: TextView = itemView.findViewById(R.id.tv_purpose_rv)
        val cv_rv_admin: CardView = itemView.findViewById(R.id.cardview_rv_admin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutpassViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return OutpassViewHolder(view)
    }

    override fun onBindViewHolder(holder: OutpassViewHolder, position: Int) {
        val outpass = OutpassList[position]
        holder.cv_rv_admin.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_rv))
        holder.tv_Name.text = outpass.name
        holder.tv_rollnum.text = outpass.rollnum
        holder.tv_arrive.text = "From: " + outpass.arrive
        holder.tv_leave.text = "To: " + outpass.leave
        holder.tv_transport.text = "Mode: " + outpass.transport
        holder.tv_purpose.text = "Purpose: " + outpass.purpose
    }

    override fun getItemCount(): Int {
        return OutpassList.size
    }

}