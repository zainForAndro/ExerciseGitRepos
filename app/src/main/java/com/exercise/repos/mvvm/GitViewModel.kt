package com.exercise.repos.mvvm

import androidx.lifecycle.MutableLiveData
import com.exercise.repos.data.models.GitData
import com.exercise.repos.dispatcher.DataSource
import com.exercise.repos.dispatcher.GitDispatcher

class GitViewModel(dispatcher: GitDispatcher): BaseViewModel(dispatcher) {


    private val liveData = MutableLiveData<List<GitData>>()

    fun getData() = liveData

    fun start(){
        fetchData<List<GitData>>(DataSource.LOCAL){
            liveData.postValue(it)
        }
    }


}