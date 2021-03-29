package com.example.emaapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emaapp.R
import com.example.emaapp.data.User

class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
    val name: TextView = itemView.findViewById(R.id.list_user_name)
    val userIcon: ImageView = itemView.findViewById(R.id.list_user_icon)
    val platformIcon: ImageView = itemView.findViewById(R.id.list_platform_id)
    val hw1: ImageView = itemView.findViewById(R.id.list_hw1)
    val hw2: ImageView = itemView.findViewById(R.id.list_hw2)
    val hw3: ImageView = itemView.findViewById(R.id.list_hw3)
    val hw4: ImageView = itemView.findViewById(R.id.list_hw4)
    val hw5: ImageView = itemView.findViewById(R.id.list_hw5)
    val hw6: ImageView = itemView.findViewById(R.id.list_hw6)
}

interface UserClickListener {
    fun onUserClick(user : User)
}

class UserAdapter(row: List<User>, private val listener: UserClickListener) : RecyclerView.Adapter<ViewHolder>() {
    var users: List<User> = row
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_row, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            listener.onUserClick(users[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(users[position].displayName)
        holder.userIcon.setImageResource(
            users[position].displayIcon
        )
        holder.platformIcon.setImageResource(
            users[position].displayPlatform
        )
        holder.hw1.setImageResource(
            users[position].hw1Started
        )
        holder.hw2.setImageResource(
            users[position].hw2Started
        )
        holder.hw3.setImageResource(
            users[position].hw3Started
        )
        holder.hw4.setImageResource(
            users[position].hw4Started
        )
        holder.hw5.setImageResource(
            users[position].hw5Started
        )
        holder.hw6.setImageResource(
            users[position].hw6Started
        )
    }

    override fun getItemCount(): Int {
        return this.users.size
    }
}

