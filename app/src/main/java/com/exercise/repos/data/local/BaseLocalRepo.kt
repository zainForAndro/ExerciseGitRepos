package com.exercise.repos.data.local

import com.exercise.repos.data.models.GitData

abstract class BaseLocalRepo {

    abstract suspend fun getLocalData() : Any?

    abstract suspend fun insertData(response: List<GitData>)


}