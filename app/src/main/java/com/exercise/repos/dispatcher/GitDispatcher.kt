package com.exercise.repos.dispatcher

import com.exercise.repos.data.local.repos.GitLocalRepo
import com.exercise.repos.data.models.Response
import com.exercise.repos.data.remote.repos.GitRemoteRepo

@Suppress("UNCHECKED_CAST")
class GitDispatcher(
    private val remoteRepo: GitRemoteRepo,
    private val localRepo: GitLocalRepo
) : BaseDispatcher() {

    override suspend fun <T> getData(dataSource: DataSource, response: (response: T?) -> Unit,
                                     error: (error: String) -> Unit) {
        if (dataSource == DataSource.LOCAL) {
            val res = localRepo.getLocalData()
            if (res == null || (res is List<*> && res.isEmpty())) {
                try {
                    val remRes = remoteRepo.getRemoteData().body()
                    if (remRes is Response) {
                        localRepo.deletePreviousData()
                        localRepo.insertData(remRes.list)
                        response(remRes.list as T)
                    } else {
                        error(remRes.toString())
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    error(e.message.toString())
                }
                return
            }
            response(res as T)
        } else {
            try {
                val res = remoteRepo.getRemoteData().body()
                if (res is Response) {
                    localRepo.deletePreviousData()
                    localRepo.insertData(res.list)
                    response(res.list as T)
                } else {
                    error(res.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                error(e.message.toString())
            }
        }
    }

}