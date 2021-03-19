package com.example.gituserlistapp.models

data class UserProfile(
    val company: String? = null,
    var login: String? = null,
    var id: String? = null,
    var avatar_url: String? = null,
    var node_id: String? = null,
    var name: String? = null,
    var blog: String? = null,
    var followers: String? = null,
    var following: String? = null
)

