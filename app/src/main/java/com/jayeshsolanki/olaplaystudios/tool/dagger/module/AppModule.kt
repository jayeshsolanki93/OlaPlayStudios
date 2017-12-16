package com.jayeshsolanki.olaplaystudios.tool.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
internal class AppModule(private val context: Context) {

    @Provides
    internal fun provideContext() = context

}
