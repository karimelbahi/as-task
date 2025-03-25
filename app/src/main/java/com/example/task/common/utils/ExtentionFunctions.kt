package com.example.task.common.utils

fun String?.convertNullToEmpty(): String {
    return this?.replace("null","") ?: ""
}