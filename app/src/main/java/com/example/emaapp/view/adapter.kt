package com.example.emaapp.view

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emaapp.R
import com.example.emaapp.model.Result
import com.example.emaapp.model.User
import java.util.Collections.addAll

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

    fun bind(user: User) {
        itemView.apply {
            name.text = user.name
            Glide.with(userIcon.context)
                .load(user.icon192)
                .into(userIcon)
            when (user.participantType) {
                "iosMentor" -> platformIcon.setImageResource(R.drawable.ic_apple)
                "iosStudent" -> platformIcon.setImageResource(R.drawable.ic_apple)
                "androidMentor" -> platformIcon.setImageResource(R.drawable.ic_android)
                "androidStudent" -> platformIcon.setImageResource(R.drawable.ic_android)
            }
    }
}

interface UserClickListener {
    fun onUserClick(user : User)
}

class UserAdapter(private val row: ArrayList<User>, private val listener: UserClickListener) : RecyclerView.Adapter<ViewHolder>() {
    var users: ArrayList<User> = row
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

        holder.bind(row[position])
//        holder.name.setText(users[position].name)
//        holder.userIcon.setImageResource(
//            users[position].displayIcon
//        )
//        holder.platformIcon.setImageResource(
//            users[position].displayPlatform
//        )
//        holder.hw1.setImageResource(
//            users[position].hw1Started
//        )
//        holder.hw2.setImageResource(
//            users[position].hw2Started
//        )
//        holder.hw3.setImageResource(
//            users[position].hw3Started
//        )
//        holder.hw4.setImageResource(
//            users[position].hw4Started
//        )
//        holder.hw5.setImageResource(
//            users[position].hw5Started
//        )
//        holder.hw6.setImageResource(
//            users[position].hw6Started
//        )
    }

    override fun getItemCount(): Int {
        return this.users.size
    }

    fun addUsers(users: List<User>) {
        this.users.apply {
            clear()
            addAll(users)
        }

    }
}
}

