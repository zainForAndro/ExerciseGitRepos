package com.exercise.repos.dispatcher

import com.exercise.repos.data.local.repos.GitLocalRepo
import com.exercise.repos.data.models.GitData
import com.exercise.repos.data.remote.repos.GitRemoteRepo

class GitDispatcher(private val remoteRepo: GitRemoteRepo,
private val localRepo: GitLocalRepo) : BaseDispatcher() {

    override suspend fun getData(dataSource: DataSource, response: (response: Any?) -> Unit) {
        if (dataSource == DataSource.LOCAL){

            localRepo.getData {
                response(it)
            }

        } else {
            remoteRepo.getData {
//                if (it is List<*>)
//                    localRepo.insertData(it as List<GitData>)
//                response(it)
            }
        }
    }

}