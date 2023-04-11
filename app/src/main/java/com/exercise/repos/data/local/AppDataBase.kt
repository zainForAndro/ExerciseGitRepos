package com.exercise.repos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exercise.repos.data.local.dao.GitDao
import com.exercise.repos.data.models.GitLocalData

@Database(entities = [GitLocalData::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getDao() : GitDao
}