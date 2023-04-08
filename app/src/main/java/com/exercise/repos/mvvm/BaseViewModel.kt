package com.exercise.repos.mvvm

import androidx.lifecycle.ViewModel
import com.exercise.repos.dispatcher.BaseDispatcher
import com.exercise.repos.dispatcher.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val dispatcher: BaseDispatcher) : ViewModel(){

    fun fetchData(dataSource: DataSource, response: (response: Object) -> Unit){

        CoroutineScope(Dispatchers.IO).launch {
            dispatcher.getData(dataSource) {

            }

        }



    }

}