package com.exercise.repos.mvvm

import com.exercise.repos.dispatcher.DataSource
import com.exercise.repos.dispatcher.GitDispatcher

class GitViewModel(dispatcher: GitDispatcher): BaseViewModel(dispatcher) {


    fun start(){
        fetchData(DataSource.LOCAL){

        }
        println("started")
    }


}