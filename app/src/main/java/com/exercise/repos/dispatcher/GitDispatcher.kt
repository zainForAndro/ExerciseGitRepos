package com.exercise.repos.dispatcher

import com.exercise.repos.data.local.repos.GitLocalRepo
import com.exercise.repos.data.models.Response
import com.exercise.repos.data.remote.repos.GitRemoteRepo

@Suppress("UNCHECKED_CAST")
class GitDispatcher(
    private val remoteRepo: GitRemoteRepo,
    private val localRepo: GitLocalRepo
) : BaseDispatcher() {

    override suspend fun <T> getData(dataSource: DataSource, response: (response: T?) -> Unit) {
        if (dataSource == DataSource.LOCAL) {
            val res = localRepo.getLocalData()
            if (res == null || (res is List<*> && res.isEmpty())) {
                val remRes = remoteRepo.getRemoteData()
                if (remRes is Response) {
                    localRepo.deletePreviousData()
                    localRepo.insertData(remRes.list)
                    response(remRes.list as T)
                }
                return
            }
            response(res as T)
        } else {
            val res = remoteRepo.getRemoteData()
            if (res is Response) {
                localRepo.deletePreviousData()
                localRepo.insertData(res.list)
                response(res.list as T)
            }
        }
    }

}