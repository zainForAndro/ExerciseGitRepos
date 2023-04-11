package com.exercise.repos.data.local.repos

import com.exercise.repos.data.local.BaseLocalRepo
import com.exercise.repos.data.local.dao.GitDao
import com.exercise.repos.data.models.GitLocalData

class GitLocalRepo(private val dao : GitDao): BaseLocalRepo() {

    override suspend fun getLocalData(): List<GitLocalData>? {
        return dao.getRepos()
    }

    override suspend fun insertData(response: List<GitLocalData>) {
        dao.insertItems(response)
    }

    suspend fun deletePreviousData() {
        dao.delete()
    }
}