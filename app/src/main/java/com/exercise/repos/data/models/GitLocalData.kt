package com.exercise.repos.data.models

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity("git_repos")
data class GitLocalData(
    @PrimaryKey(autoGenerate = true)
    var key : Long = 0,
    @SerializedName("name")
    var name : String? = null,
    @SerializedName("full_name")
    var full_name : String? = null,
    @SerializedName("description")
    var description : String? = null,
    @SerializedName("language")
    var language : String? = null,
    @SerializedName("score")
    var score : String? = null,
    @SerializedName("avatar_url")
    var avatarUrl: String? = null
)