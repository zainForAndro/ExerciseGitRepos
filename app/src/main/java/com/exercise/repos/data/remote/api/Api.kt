package com.exercise.repos.data.remote.api

import com.exercise.repos.data.models.Response
import retrofit2.http.GET

interface Api {


    @GET("repositories?q=language=+sort:stars")
    suspend fun getRepos() : retrofit2.Response<Response>

}