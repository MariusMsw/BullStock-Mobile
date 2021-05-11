package com.mariusmihai.bullstock.core.helpers

import android.util.Patterns

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()