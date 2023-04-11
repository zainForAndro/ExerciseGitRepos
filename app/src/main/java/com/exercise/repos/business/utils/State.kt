package com.exercise.repos.business.utils

sealed class State {
    open class Loading : State()
    open class Error : State()
}
