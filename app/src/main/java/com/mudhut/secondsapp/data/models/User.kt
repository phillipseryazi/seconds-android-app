package com.mudhut.secondsapp.data.models

data class User(
    var id: Int = 0,
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var token: String = ""
)
