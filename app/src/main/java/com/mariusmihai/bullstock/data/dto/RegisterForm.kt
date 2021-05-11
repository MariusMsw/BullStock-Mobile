package com.mariusmihai.bullstock.data.dto

data class RegisterForm(
    val email: String,
    val password: String,
    val confirmPassword: String
)