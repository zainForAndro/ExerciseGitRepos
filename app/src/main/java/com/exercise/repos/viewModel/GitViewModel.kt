package com.exercise.repos.viewModel

import androidx.lifecycle.MutableLiveData
import com.exercise.repos.data.models.GitLocalData
import com.exercise.repos.business.dispatcher.DataSource
import com.exercise.repos.business.dispatcher.GitDispatcher
import com.exercise.repos.business.utils.State

class GitViewModel(dispatcher: GitDispatcher): BaseViewModel(dispatcher) {


    private val liveData = MutableLiveData<List<GitLocalData>>()
    private val states = MutableLiveData<State>()
    fun getState() = states

    fun getData() = liveData

    fun start(source: DataSource = DataSource.LOCAL){
        handleStates(State.Loading())
        fetchData<List<GitLocalData>>(source){
            liveData.postValue(it)
        }
    }

    override fun handleStates(state: State) {
        states.postValue(state)
    }


}