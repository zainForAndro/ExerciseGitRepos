package com.exercise.repos.mvvm

import androidx.lifecycle.ViewModel
import com.exercise.repos.dispatcher.BaseDispatcher
import com.exercise.repos.dispatcher.DataSource
import com.exercise.repos.utils.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
abstract class BaseViewModel(private val dispatcher: BaseDispatcher) : ViewModel(){

    fun <T>fetchData(dataSource: DataSource, response: (response: T?) -> Unit){

        CoroutineScope(Dispatchers.IO).launch {
            dispatcher.getData<T>(dataSource, {
                response(it)
            }, { error ->
                handleStates(State.Error())
            }
            )

        }

    }

    abstract fun handleStates(state: State)

}