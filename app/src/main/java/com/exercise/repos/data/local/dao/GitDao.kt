package com.exercise.repos.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exercise.repos.data.models.GitData

@Dao
interface GitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(response: List<GitData>)

    @Query("SELECT * FROM ReposResponse")
    fun getRepos() : LiveData<List<GitData>>

}