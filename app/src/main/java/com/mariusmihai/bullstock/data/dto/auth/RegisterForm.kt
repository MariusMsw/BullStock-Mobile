package com.mariusmihai.bullstock.data.dto.auth

data class RegisterForm(
    val email: String,
    val password: String,
    val confirmPassword: String
)