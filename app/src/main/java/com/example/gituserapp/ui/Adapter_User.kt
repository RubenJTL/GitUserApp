package com.example.gituserapp.ui

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.gituserapp.R
import com.example.gituserapp.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_item.view.*

class Adapter_User(val items : ArrayList<User>, val context: Context, val clickListener: (User) -> Unit) : RecyclerView.Adapter<UserHolder>() {


    override fun onBindViewHolder(p0: UserHolder, p1: Int) {
        p0.nameLocation.text = (items[p1].name + ", " + items[p1].location)
        p0.login.text = items[p1].login
        Picasso.get().load(items[p1].avatarUrl).into(p0.avatar)
        (p0).bind(items[p1], clickListener)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UserHolder {
        return UserHolder(LayoutInflater.from(context).inflate(R.layout.user_item, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}


class UserHolder (view: View) : RecyclerView.ViewHolder(view) {
    fun bind(part: User, clickListener: (User) -> Unit){
        itemView.setOnClickListener{clickListener(part)}
    }
    val avatar : ImageView = view.avatar
    val nameLocation : TextView = view.location
    val login : TextView = view.login
}