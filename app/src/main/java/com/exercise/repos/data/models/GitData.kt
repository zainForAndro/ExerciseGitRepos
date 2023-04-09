package com.exercise.repos.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName



data class Response(

    @SerializedName("items")
    val list: List<GitData>
)
@Entity("git_repos")
data class GitData(
    @PrimaryKey(autoGenerate = true)
    var key : Int,
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
    var avatarUrl : String? = null,

    )