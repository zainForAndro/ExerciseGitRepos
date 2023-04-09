package com.exercise.repos.utils

sealed class State {
    open class Loading : State()
    open class Error : State()
}
