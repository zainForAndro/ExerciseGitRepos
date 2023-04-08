package com.exercise.repos.dispatcher

import com.exercise.repos.data.local.BaseLocalRepo
import com.exercise.repos.data.models.GitData
import com.exercise.repos.data.remote.BaseRemoteRepo
import kotlinx.coroutines.delay

abstract class BaseDispatcher {

    abstract suspend fun getData(dataSource: DataSource, response: (response: Any?) -> Unit)

}

enum class DataSource{
    LOCAL,
    REMOTE
}