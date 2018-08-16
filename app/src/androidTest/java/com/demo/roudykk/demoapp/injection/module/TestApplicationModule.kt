package com.demo.roudykk.demoapp.injection.module

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides

@Module
object TestApplicationModule {

    @JvmStatic
    @Provides
    fun bindContext(): Context {
        return mock()
    }
}