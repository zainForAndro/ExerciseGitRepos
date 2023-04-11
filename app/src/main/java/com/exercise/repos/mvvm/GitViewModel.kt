package com.exercise.repos.mvvm

import androidx.lifecycle.MutableLiveData
import com.exercise.repos.data.models.GitLocalData
import com.exercise.repos.dispatcher.DataSource
import com.exercise.repos.dispatcher.GitDispatcher
import com.exercise.repos.utils.State

class GitViewModel(dispatcher: GitDispatcher): BaseViewModel(dispatcher) {


    private val liveData = MutableLiveData<List<GitLocalData>>()
    private val states = MutableLiveData<State>()

    fun getData() = liveData

    fun start(){
        handleStates(State.Loading())
        fetchData<List<GitLocalData>>(DataSource.LOCAL){
            liveData.postValue(it)
        }
    }

    override fun handleStates(state: State) {
        states.postValue(state)
    }


}