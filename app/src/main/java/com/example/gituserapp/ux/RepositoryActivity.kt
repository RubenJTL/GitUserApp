package com.example.gituserapp.ux

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.example.gituserapp.R
import com.example.gituserapp.model.Repository
import com.example.gituserapp.ui.Adapter_Repository
import kotlinx.android.synthetic.main.activity_repositories.*

class RepositoryActivity : AppCompatActivity() {

    val repositories: ArrayList<Repository> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)
        val username:String = intent.getStringExtra("user_name")
        val context = this
        ViewUserName(username)
        recycler_Repository.layoutManager=LinearLayoutManager(this)

        repositories.add(Repository("tratos.com","ni idea","probando",1))
        repositories.add(Repository("tratos2.com","ni idea2","probando2",0))
        repositories.add(Repository("tratos3.com","ni idea3","probando3",0))

        progress_bar.visibility = View.VISIBLE
        recycler_Repository.adapter = Adapter_Repository(repositories, context)
        progress_bar.visibility = View.INVISIBLE
    }

    fun ViewUserName(username:String)
    {
        val name= findViewById(R.id.user_name) as TextView
        name.setText(username)
    }
}