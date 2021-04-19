package com.example.emaapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.emaapp.R
import com.example.emaapp.data.ParticipantType
import com.example.emaapp.data.User

class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
    val name: TextView = itemView.findViewById(R.id.list_user_name)
    private val userIcon: ImageView = itemView.findViewById(R.id.list_user_icon)
    private val platformIcon: ImageView = itemView.findViewById(R.id.list_platform_id)
    private val hw1: ImageView = itemView.findViewById(R.id.list_hw1)
    private val hw2: ImageView = itemView.findViewById(R.id.list_hw2)
    private val hw3: ImageView = itemView.findViewById(R.id.list_hw3)
    private val hw4: ImageView = itemView.findViewById(R.id.list_hw4)
    private val hw5: ImageView = itemView.findViewById(R.id.list_hw5)
    private val hw6: ImageView = itemView.findViewById(R.id.list_hw6)

    fun bind(user: User) {
        itemView.apply {
            name.text = user.name
            Glide.with(userIcon.context)
                .load(user.icon192)
                .error(R.drawable.no_image_icon)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.loading_icon)
                .into(userIcon)
            when (user.participantType) {
                ParticipantType.ANDROID_MENTOR, ParticipantType.ANDROID_STUDENT -> platformIcon.setImageResource(
                    R.drawable.ic_android)
                ParticipantType.IOS_MENTOR, ParticipantType.IOS_STUDENT -> platformIcon.setImageResource(
                    R.drawable.ic_apple)
                ParticipantType.WATCHER -> platformIcon.setImageResource(R.drawable.watcher)
            }
            when (user.homework[0].state) {
                "comingsoon" -> hw1.setImageResource(R.drawable.ic_one)
                else -> hw1.setImageResource(R.drawable.ic_one_done)
            }
            when (user.homework[1].state) {
                "comingsoon" -> hw2.setImageResource(R.drawable.ic_two)
                else -> hw2.setImageResource(R.drawable.ic_two_done)
            }
            when (user.homework[2].state) {
                "comingsoon" -> hw3.setImageResource(R.drawable.ic_three)
                else -> hw3.setImageResource(R.drawable.ic_three_done)
            }
            when (user.homework[3].state) {
                "comingsoon" -> hw4.setImageResource(R.drawable.ic_four)
                else -> hw4.setImageResource(R.drawable.ic_four_done)
            }
            when (user.homework[4].state) {
                "comingsoon" -> hw5.setImageResource(R.drawable.ic_five)
                else -> hw5.setImageResource(R.drawable.ic_five_done)
            }
            when (user.homework[5].state) {
                "comingsoon" -> hw6.setImageResource(R.drawable.ic_six)
                else -> hw6.setImageResource(R.drawable.ic_six_done)
            }
        }
    }


    interface UserClickListener {
        fun onUserClick(user: User)
    }

    class UserAdapter(private val row: ArrayList<User>, private val listener: UserClickListener) :
        RecyclerView.Adapter<ViewHolder>() {
        private var users: ArrayList<User> = row
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.layout_row, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.itemView.setOnClickListener {
                listener.onUserClick(users[viewHolder.adapterPosition])
            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(row[position])
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

