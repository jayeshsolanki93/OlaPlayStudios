package com.jayeshsolanki.olaplaystudios.tool.dagger.component

import android.content.Context
import android.content.SharedPreferences
import com.jayeshsolanki.olaplaystudios.tool.dagger.module.AppModule
import com.jayeshsolanki.olaplaystudios.tool.dagger.module.DataModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class))
interface DataComponent {

    fun context(): Context
    fun retrofit(): Retrofit
    fun sharedPreferences(): SharedPreferences
}
