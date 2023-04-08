package com.exercise.repos.data.local.repos

import com.exercise.repos.data.local.BaseLocalRepo
import com.exercise.repos.data.local.dao.GitDao

class GitLocalRepo(private val dao : GitDao): BaseLocalRepo() {

    override suspend fun getData(response: (response: Any?) -> Unit) {
        response(dao.getRepos())
    }
}