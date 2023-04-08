package com.exercise.repos.data.local.repos

import com.exercise.repos.data.local.BaseLocalRepo
import com.exercise.repos.data.local.dao.GitDao
import com.exercise.repos.data.models.GitData

class GitLocalRepo(private val dao : GitDao): BaseLocalRepo() {

    override suspend fun getData(response: (response: Any?) -> Unit) {
        response(dao.getRepos())
    }

    override suspend fun insertData(response: List<GitData>) {
        dao.insertItems(response)
    }
}