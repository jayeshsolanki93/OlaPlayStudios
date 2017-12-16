package com.jayeshsolanki.olaplaystudios.ui.songslist

import com.jayeshsolanki.olaplaystudios.data.source.songs.dagger.SongsRepositoryComponent
import com.jayeshsolanki.olaplaystudios.tool.dagger.scope.FragmentScoped
import dagger.Component

@FragmentScoped
@Component(
        modules = arrayOf(SongsListPresenterModule::class),
        dependencies = arrayOf(SongsRepositoryComponent::class))
interface SongsListComponent {

    fun inject(activity: SongsListActivity)
}
