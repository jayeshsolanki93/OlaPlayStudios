package com.jayeshsolanki.olaplaystudios.ui.songslist

import com.jayeshsolanki.olaplaystudios.data.model.Song
import com.jayeshsolanki.olaplaystudios.ui.BasePresenter
import com.jayeshsolanki.olaplaystudios.ui.BaseView

interface SongsListContract {

    interface Presenter: BasePresenter {
        fun loadSongsList()
    }

    interface View : BaseView<Presenter> {
        fun showSongsList(songs: List<Song>)

        fun showError(message: String)
    }

}
