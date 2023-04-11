package com.exercise.repos.viewModel

import androidx.lifecycle.ViewModel
import com.exercise.repos.business.dispatcher.BaseDispatcher
import com.exercise.repos.business.dispatcher.DataSource
import com.exercise.repos.business.utils.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
abstract class BaseViewModel(private val dispatcher: BaseDispatcher) : ViewModel(){

    fun <T>fetchData(dataSource: DataSource, response: (response: T?) -> Unit){
        val scope = CoroutineScope(IO)
        scope.launch {
            dispatcher.getData<T>(dataSource, {
                scope.launch {
                    withContext(Main) {
                        response(it)
                    }
                }
            }, { _ ->
                scope.launch {
                    withContext(Main){
                        handleStates(State.Error())
                    }
                }
            }
            )

        }

    }

    abstract fun handleStates(state: State)

}