package com.exercise.repos.data.remote.api

import com.exercise.repos.data.models.remote.GitReposResponse
import retrofit2.http.GET

interface Api {


    @GET("repositories?q=language=+sort:stars")
    suspend fun getRepos() : retrofit2.Response<GitReposResponse>

}