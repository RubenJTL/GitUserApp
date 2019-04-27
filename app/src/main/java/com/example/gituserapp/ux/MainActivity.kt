package com.example.gituserapp.ux

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.Github.UsersQuery
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.gituserapp.R
import com.example.gituserapp.model.User
import com.example.gituserapp.ui.Adapter_User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_view.*
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    var users:ArrayList<User> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this

        recycler_User.layoutManager = LinearLayoutManager(this)

        val client = setupApollo()

        val searchButton = findViewById(R.id.imageButton_Search) as ImageButton

        searchButton.setOnClickListener {
            users.clear()
            progress_bar.visibility = View.VISIBLE
            dataBaseUser(client,context)

            progress_bar.visibility = View.INVISIBLE
        }

    }


    fun dataBaseUser(client: ApolloClient,context: Context){
        client.query(UsersQuery
            .builder()
            .queryText(editText_Search.text.toString())
            .build())
            .enqueue(object : ApolloCall.Callback<UsersQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    progress_bar.visibility = View.GONE
                    Log.d("Error Message: ", e.message.toString())
                    Log.d("Trace Error: ", e.printStackTrace().toString())
                    progress_bar.visibility = View.INVISIBLE
                }
                override fun onResponse(response: Response<UsersQuery.Data>) {
                    Log.d("OK Message: ", response.data().toString())
                    runOnUiThread {
                        val queryResult = response.data()?.search()?.edges()
                        queryResult!!.forEach {
                            var nodeString = it.node()
                            users.add(HelperJSON.getUser(HelperJSON.toJsonString(nodeString)))
                        }
                        System.out.println(users.count());
                        emptyViewVisibility(users.count());
                        recycler_User.adapter = Adapter_User(users, context, { partItem : User -> partItemClicked(partItem)})
                    }
                }
            })
    }
    fun emptyViewVisibility(tam:Int){
        if (tam==0){
            System.out.println("esta aqui");
            empty_view_include.visibility=View.VISIBLE
        }else{
            empty_view_include.visibility=View.INVISIBLE
        }
    }
    fun setupApollo(): ApolloClient {
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method(),
                    original.body())
                builder.addHeader("Authorization"
                    , "Bearer " + "<your token>")
                chain.proceed(builder.build())
            }
            .build()
        return ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(okHttp)
            .build()
    }

    object HelperJSON {
        private var gson: Gson = Gson()
        fun getUser(jsonString: String): User {
            Log.e("JSONHelper ", "Enter: " + jsonString)
            return gson.fromJson(jsonString, User::class.java)
        }

        fun toJsonString(simpleObject: Any?): String = gson.toJson(simpleObject)
    }

    private fun partItemClicked(partItem : User) {
        val intent = Intent(this@MainActivity, RepositoryActivity::class.java)
        intent.putExtra("user_name", partItem.login)
        startActivity(intent)
    }
}