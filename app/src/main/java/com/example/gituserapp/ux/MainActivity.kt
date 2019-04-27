package com.example.gituserapp.ux

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageButton
import com.example.gituserapp.R
import com.example.gituserapp.model.User
import com.example.gituserapp.ui.Adapter_User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val users:ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this
        users.add(User("ruben_jtl","ruben","",R.drawable.ic_github))
        users.add(User("ruben_jtl@nsa","Jesus","Peru",R.drawable.ic_github))
        recycler_User.layoutManager = LinearLayoutManager(this)
        val searchButton = findViewById(R.id.imageButton_Search) as ImageButton

        searchButton.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            recycler_User.adapter = Adapter_User(users, context, { partItem : User -> partItemClicked(partItem)})
            progress_bar.visibility = View.INVISIBLE
        }

    }

    private fun partItemClicked(partItem : User) {
        val intent = Intent(this@MainActivity, RepositoryActivity::class.java)
        intent.putExtra("user_name", partItem.login)
        startActivity(intent)
    }
}
