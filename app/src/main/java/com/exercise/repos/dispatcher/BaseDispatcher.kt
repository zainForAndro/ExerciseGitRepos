package com.exercise.repos.dispatcher

import com.exercise.repos.data.local.BaseLocalRepo
import com.exercise.repos.data.models.GitData
import com.exercise.repos.data.remote.BaseRemoteRepo
import kotlinx.coroutines.delay

abstract class BaseDispatcher {

    abstract suspend fun <T>getData(dataSource: DataSource, response: (response: T?) -> Unit)

}

enum class DataSource{
    LOCAL,
    REMOTE
}