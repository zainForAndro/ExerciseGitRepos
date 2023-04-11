package com.exercise.repos.business.dispatcher


abstract class BaseDispatcher {

    abstract suspend fun <T>getData(dataSource: DataSource, response: (response: T?) -> Unit,
                                    error: (error: String) -> Unit)

}

enum class DataSource{
    LOCAL,
    REMOTE
}