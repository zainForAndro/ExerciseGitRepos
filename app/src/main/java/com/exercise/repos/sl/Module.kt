package com.exercise.repos.sl

import androidx.room.Room
import com.exercise.repos.data.local.AppDataBase
import com.exercise.repos.data.local.dao.GitDao
import com.exercise.repos.data.local.repos.GitLocalRepo
import com.exercise.repos.data.remote.Service
import com.exercise.repos.data.remote.repos.GitRemoteRepo
import com.exercise.repos.dispatcher.GitDispatcher
import com.exercise.repos.mvvm.BaseViewModel
import com.exercise.repos.mvvm.GitViewModel
import get
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val client = module {
    single { Service.getService() }
}

val gitRepos = module {

    single {
        Room.databaseBuilder(androidApplication(),
        AppDataBase::class.java,
        "app-db").build()
    }
    single { get<AppDataBase>().getDao() }
    single { GitRemoteRepo(api = get()) }
    single { GitLocalRepo(dao = get()) }
    single { GitDispatcher(remoteRepo = get(), localRepo = get()) }
    viewModel { GitViewModel(dispatcher = get()) }

}