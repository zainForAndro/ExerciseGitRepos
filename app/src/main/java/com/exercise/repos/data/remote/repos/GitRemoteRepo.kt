package com.exercise.repos.data.remote.repos

import com.exercise.repos.data.remote.api.Api
import com.exercise.repos.data.remote.BaseRemoteRepo

class GitRemoteRepo(api: Api): BaseRemoteRepo() {

    override suspend fun getData(response: (response: Any?) -> Unit) {

    }
}