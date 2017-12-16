package com.jayeshsolanki.olaplaystudios.ui.songslist

import dagger.Module
import dagger.Provides

@Module
class SongsListPresenterModule(private val view: SongsListContract.View) {

    @Provides
    internal fun providesSongsListContractView() = view
}
