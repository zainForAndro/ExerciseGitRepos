package com.exercise.repos.data.remote.api

import retrofit2.http.GET

interface Api {


    @GET
    suspend fun getRepos()

}