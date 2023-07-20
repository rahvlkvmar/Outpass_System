package com.example.outpasssystem

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class OutpassStudentAdapter(private val OutpassList: ArrayList<OutpassStudentRV>, private val Status: String? = null)
    : RecyclerView.Adapter<OutpassStudentAdapter.OutpassViewHolder>(){

    class OutpassViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tv_leave_student: TextView = itemView.findViewById(R.id.tv_leave_student)
        val tv_arrive_student : TextView = itemView.findViewById(R.id.tv_arrive_student)
        val tv_status_student : TextView = itemView.findViewById(R.id.tv_status_student)
        val cv_rv_student: CardView = itemView.findViewById(R.id.cardview_rv_student)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutpassViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_student, parent, false)
        return OutpassViewHolder(view)

    }

    override fun onBindViewHolder(holder: OutpassViewHolder, position: Int) {
        val outpass = OutpassList[position]
        holder.tv_leave_student.text = "From: " + outpass.leave
        holder.tv_arrive_student.text = "Arrive: " + outpass.arrive
        holder.tv_status_student.text = outpass.status
        holder.cv_rv_student.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_rv))
        if (Status == "Pending"){
            holder.tv_status_student.setTextColor(Color.parseColor("#FFBB33"))
        }
        else if (Status == "Approved"){
            holder.tv_status_student.setTextColor(Color.parseColor("#99CC00"))
        }
        else if (Status == "Rejected"){
            holder.tv_status_student.setTextColor(Color.parseColor("#CC0000"))
        }

    }

    override fun getItemCount(): Int {
        return OutpassList.size
    }

}