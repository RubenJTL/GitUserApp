package com.example.gituserapp.ux

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.TextView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.Github.RepositoriesQuery
import com.apollographql.apollo.api.Response

import com.apollographql.apollo.exception.ApolloException
import com.example.gituserapp.R
import com.example.gituserapp.model.Repository
import com.example.gituserapp.ui.Adapter_Repository
import kotlinx.android.synthetic.main.activity_repositories.*
import okhttp3.OkHttpClient

class RepositoryActivity : AppCompatActivity() {

    val repositories: ArrayList<Repository> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)

        val username:String = intent.getStringExtra("user_name")
        val context = this

        recycler_Repository.layoutManager = LinearLayoutManager(this)

        val client = setupApollo()
        user_name.setText(username)
        client.query(RepositoriesQuery
            .builder()
            .user_name(username)
            .build())
            .enqueue(object : ApolloCall.Callback<RepositoriesQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d("ss",e.message.toString())
                    Log.d("ss",e.printStackTrace().toString())
                }

                override fun onResponse(response: Response<RepositoriesQuery.Data>) {
                    Log.d("ssdd"," " + response.data()?.user())
                    runOnUiThread {
                        progress_bar.visibility = View.VISIBLE
                        //user_name.setText(response.data()?.user()?.repositories().toString())
                        val repos = response.data()?.user()?.repositories()?.edges()
                        repos!!.forEach {
                            repositories.add(
                                Repository(
                                    name = it.node()!!.name(),
                                    url = it.node()!!.url(),
                                    description = it.node()!!.description().toString(),
                                    PR_count = it.node()!!.pullRequests().totalCount()
                                )
                            )
                        }
                        recycler_Repository.adapter = Adapter_Repository(repositories, context)
                        progress_bar.visibility = View.INVISIBLE
                    }

                }
            })

    }

    private fun setupApollo(): ApolloClient {
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method(),
                    original.body())
                builder.addHeader("Authorization"
                    , "Bearer " + "<YOUR TOKEN>")
                chain.proceed(builder.build())
            }
            .build()
        return ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(okHttp)
            .build()
    }

    fun ViewUserName(username:String)
    {
        val name= findViewById(R.id.user_name) as TextView
        name.setText(username)
    }
}