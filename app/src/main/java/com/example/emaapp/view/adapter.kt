package com.example.emaapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emaapp.R
import com.example.emaapp.data.User

//OLD VERSION - CAN BE DELETED LATER ON
//class ViewHolder(val row: TextView) : RecyclerView.ViewHolder(row) {
//}
//
//class Adapter(var users: List<User>) : RecyclerView.Adapter<ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.layout_row, parent, false) as TextView
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.row.setText(users[position].displayName)
//        holder.row.setCompoundDrawablesRelativeWithIntrinsicBounds(
//            users[position].icon,
//            0,
//            0,
//            0
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return users.size
//    }
//}

class Adapter(var users: List<User>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(users[position].displayName)
        holder.userIcon.setImageResource(
            users[position].displayIcon
        )
        holder.platformIcon.setImageResource(
            users[position].displayPlatform
        )
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val name = itemView.findViewById<TextView>(R.id.list_user_name)!!
        val userIcon = itemView.findViewById<ImageView>(R.id.list_user_icon)!!
        val platformIcon = itemView.findViewById<ImageView>(R.id.list_platform_id)!!

    }

    override fun getItemCount(): Int {
        return this.users.size
    }
}
