package com.jayeshsolanki.olaplaystudios

import android.app.Application
import com.jayeshsolanki.olaplaystudios.data.source.songs.dagger.DaggerSongsRepositoryComponent
import com.jayeshsolanki.olaplaystudios.data.source.songs.dagger.SongsRepositoryComponent
import com.jayeshsolanki.olaplaystudios.data.source.songs.dagger.SongsRepositoryModule
import com.jayeshsolanki.olaplaystudios.tool.dagger.component.DaggerDataComponent
import com.jayeshsolanki.olaplaystudios.tool.dagger.component.DataComponent
import com.jayeshsolanki.olaplaystudios.tool.dagger.module.AppModule
import com.jayeshsolanki.olaplaystudios.tool.dagger.module.DataModule
import com.jayeshsolanki.olaplaystudios.util.Constants

class OlaPlayStudios : Application() {

    lateinit var dataComponent: DataComponent
        internal set

    lateinit var songsRepositoryComponent: SongsRepositoryComponent
        internal set

    override fun onCreate() {
        super.onCreate()

        this.initializeInjector()
    }

    private fun initializeInjector() {
        dataComponent = DaggerDataComponent.builder()
                .appModule(AppModule(this))
                .dataModule(DataModule(Constants.API_URL))
                .build()

        songsRepositoryComponent = DaggerSongsRepositoryComponent.builder()
                .dataComponent(dataComponent)
                .songsRepositoryModule(SongsRepositoryModule())
                .build()
    }

}
