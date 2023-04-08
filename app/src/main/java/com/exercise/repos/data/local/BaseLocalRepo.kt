package com.exercise.repos.data.local

abstract class BaseLocalRepo {

    abstract suspend fun getData(response: (response: Any?) -> Unit)

}