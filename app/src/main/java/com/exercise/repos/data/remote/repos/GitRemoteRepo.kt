package com.exercise.repos.data.remote.repos

import com.exercise.repos.data.remote.api.Api
import com.exercise.repos.data.remote.BaseRemoteRepo
import retrofit2.Response

class GitRemoteRepo(private val api: Api): BaseRemoteRepo() {

    override suspend fun getRemoteData() : Response<*> {
        return api.getRepos()
    }
}