package com.exercise.repos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.exercise.repos.data.local.dao.GitDao
import com.exercise.repos.data.models.GitData

@Database(entities = [GitData::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getDao() : GitDao


    companion object {
        private var instance : AppDataBase? = null

        fun getAppDataBase(context: Context): AppDataBase {

            synchronized(AppDataBase::class.java) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "app-db"
                    ).build()
                }
            }

            return instance!!
        }
    }
}