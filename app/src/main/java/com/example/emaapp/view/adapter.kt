package com.example.emaapp.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emaapp.R
import com.example.emaapp.data.User
import kotlinx.coroutines.flow.callbackFlow


class Adapter(var row: List<User>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    var users: List<User> = row
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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
        holder.hw1.setImageResource(
            users[position].hw1_started
        )
        holder.hw2.setImageResource(
            users[position].hw2_started
        )
        holder.hw3.setImageResource(
            users[position].hw3_started
        )
        holder.hw4.setImageResource(
            users[position].hw4_started
        )
        holder.hw5.setImageResource(
            users[position].hw5_started
        )
        holder.hw6.setImageResource(
            users[position].hw6_started
        )
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val name = itemView.findViewById<TextView>(R.id.list_user_name)
        val userIcon = itemView.findViewById<ImageView>(R.id.list_user_icon)
        val platformIcon = itemView.findViewById<ImageView>(R.id.list_platform_id)
        val hw1 = itemView.findViewById<ImageView>(R.id.list_hw1)
        val hw2 = itemView.findViewById<ImageView>(R.id.list_hw2)
        val hw3 = itemView.findViewById<ImageView>(R.id.list_hw3)
        val hw4 = itemView.findViewById<ImageView>(R.id.list_hw4)
        val hw5 = itemView.findViewById<ImageView>(R.id.list_hw5)
        val hw6 = itemView.findViewById<ImageView>(R.id.list_hw6)

        init {
            row.setOnClickListener {
                val intent = Intent(row.context, UserProfileActivity::class.java)
                row.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return this.users.size
    }
}

