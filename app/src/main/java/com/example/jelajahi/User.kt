package com.example.jelajahi

import java.io.Serializable

data class User(
    val id: Long,
    val user_id: Long,
    val first_name: String,
    val last_name: String,
    val username: String,
    val password: String
) : Serializable