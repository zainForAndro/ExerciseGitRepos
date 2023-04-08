package com.exercise.repos.data.remote

import com.exercise.repos.data.remote.api.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {

    private var api: Api? = null

    fun getService() : Api {
        if (api == null){
            api = Retrofit.Builder().baseUrl("https://api.github.com/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build().create(Api::class.java)
        }
        return api!!
    }


    private fun getClient() : OkHttpClient{
        return OkHttpClient.Builder().build()
    }

}