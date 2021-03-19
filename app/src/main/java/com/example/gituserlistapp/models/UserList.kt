package com.example.gituserlistapp.models

import com.google.gson.annotations.SerializedName

class UserList {

    @SerializedName("login")
    lateinit var login: String

    @SerializedName("id")
    lateinit var id: String

    @SerializedName("avatar_url")
    lateinit var avatar_url: String

    @SerializedName("node_id")
    lateinit var node_id: String

    @SerializedName("received_events_url")
    lateinit var received_events_url: String

    @SerializedName("type")
    lateinit var type: String

}