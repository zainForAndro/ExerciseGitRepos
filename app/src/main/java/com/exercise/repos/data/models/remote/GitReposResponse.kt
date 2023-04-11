package com.exercise.repos.data.models.remote

import com.google.gson.annotations.SerializedName

data class GitReposResponse (
    @SerializedName("items")
    val list: List<GitRemoteData>

    )

data class GitRemoteData(
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
    @SerializedName("owner")
    var owner: Owner? = null,
    )

data class Owner(
    @SerializedName("avatar_url")
    var avatarUrl : String? = null
)