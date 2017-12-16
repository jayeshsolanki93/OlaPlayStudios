package com.jayeshsolanki.olaplaystudios.data.source.songs.dagger

import com.jayeshsolanki.olaplaystudios.data.source.songs.SongsRepository
import com.jayeshsolanki.olaplaystudios.tool.dagger.component.DataComponent
import com.jayeshsolanki.olaplaystudios.tool.dagger.scope.CustomScoped
import dagger.Component

@CustomScoped
@Component(
        modules = arrayOf(SongsRepositoryModule::class),
        dependencies = arrayOf(DataComponent::class))
interface SongsRepositoryComponent {

    fun songsRepositoryComponent(): SongsRepository
}
