package com.exercise.repos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exercise.repos.data.models.GitData

@Dao
interface GitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(response: List<GitData>)

    @Query("SELECT * FROM git_repos")
    suspend fun getRepos() : List<GitData>?
    @Query("DELETE FROM git_repos")
    suspend fun delete()

}