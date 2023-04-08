package com.exercise.repos.data.remote

abstract class BaseRemoteRepo {

    abstract suspend fun getData(response: (response: Any?) -> Unit)

}