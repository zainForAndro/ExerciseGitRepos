package com.exercise.repos

import android.app.Application
import com.exercise.repos.sl.client
import com.exercise.repos.sl.gitRepos
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class GitReposApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GitReposApplication)
            modules(
                listOf(
                    client,
                    gitRepos,
                )
            )
        }
    }
}