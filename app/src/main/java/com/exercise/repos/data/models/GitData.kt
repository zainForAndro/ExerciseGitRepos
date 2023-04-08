package com.exercise.repos.data.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity("git_repos")
data class GitData(
    @SerializedName("name")
    val name : String? = null,
    @SerializedName("full_name")
    val full_name : String? = null,
    @SerializedName("description")
    val description : String? = null,
    @SerializedName("language")
    val language : String? = null,
    @SerializedName("score")
    val score : String? = null,
    @SerializedName("avatar_url")
    val avatarUrl : String? = null,

    )