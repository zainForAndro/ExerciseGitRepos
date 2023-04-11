package com.exercise.repos.business.dispatcher

import com.exercise.repos.data.local.repos.GitLocalRepo
import com.exercise.repos.data.models.GitLocalData
import com.exercise.repos.data.models.remote.GitRemoteData
import com.exercise.repos.data.models.remote.GitReposResponse
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
            if (res == null || res.isEmpty()) {
                val remRes = try {
                     remoteRepo.getRemoteData().body()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    e.message
                }
                if (remRes != null && remRes is GitReposResponse) {
                    localRepo.deletePreviousData()
                    val localMappedData = getMappedResponse(remRes.list)
                    localRepo.insertData(localMappedData)
                    response(localMappedData as T)
                } else {
                    error(remRes.toString())
                }
                return
            }
            response(res as T)
        } else {
            val remRes = try {
                remoteRepo.getRemoteData().body()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                e.message
            }
            if (remRes != null && remRes is GitReposResponse) {
                localRepo.deletePreviousData()
                val localMappedData = getMappedResponse(remRes.list)
                localRepo.insertData(localMappedData)
                response(localMappedData as T)
            } else {
                val localResponse = localRepo.getLocalData()
                if (localResponse != null && localResponse.isNotEmpty()){
                    response(localResponse as T)
                } else {
                    error(remRes.toString())
                }
            }
        }
    }

    private fun getMappedResponse(remoteResponse: List<GitRemoteData>) : List<GitLocalData>{
        val list = ArrayList<GitLocalData>()
        for (i in remoteResponse){
            val item = GitLocalData(
                name = i.name,
                full_name = i.full_name,
                description = i.description,
                language = i.language,
                score = i.score,
                avatarUrl = i.owner?.avatarUrl,
            )
            list.add(item)
        }
        return list
    }

}