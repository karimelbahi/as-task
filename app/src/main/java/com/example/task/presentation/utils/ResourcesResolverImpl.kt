package com.example.task.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesResolverImpl @Inject constructor(@ApplicationContext private val context: Context) :
    ResourcesResolver {

    override fun getString(@StringRes resId: Int): String = context.getString(resId)

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any?) =
        context.getString(resId, *formatArgs)

}