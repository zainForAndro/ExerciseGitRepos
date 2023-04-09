package com.exercise.repos.data.remote.repos

import com.exercise.repos.data.remote.api.Api
import com.exercise.repos.data.remote.BaseRemoteRepo

class GitRemoteRepo(private val api: Api): BaseRemoteRepo() {

    override suspend fun getRemoteData() : Any? {
        return api.getRepos().body()
    }
}