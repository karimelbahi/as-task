package com.example.task.presentation.utils

import androidx.annotation.StringRes

interface ResourcesResolver {
    fun getString(@StringRes resId: Int): String
    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String
}