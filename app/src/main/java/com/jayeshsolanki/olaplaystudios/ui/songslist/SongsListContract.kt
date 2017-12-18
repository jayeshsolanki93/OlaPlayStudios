package com.jayeshsolanki.olaplaystudios.ui.songslist

import com.jayeshsolanki.olaplaystudios.data.model.Song
import com.jayeshsolanki.olaplaystudios.ui.BasePresenter
import com.jayeshsolanki.olaplaystudios.ui.BaseView

interface SongsListContract {

    interface Presenter: BasePresenter {

        /**
         * Gets and prepares the songs list for the UI.
         */
        fun loadSongsList()
    }

    interface View : BaseView<Presenter> {

        /** Use this variable to decide on showing 'User Playlist' or 'Songs from the API' */
        var viewType: String

        /**
         * Show a lists of songs on the UI.
         *
         * @param songs Songs metadata
         */
        fun showSongsList(songs: List<Song>)

        /**
         * Load and show songs saved in the playlist by the user.
         */
        fun loadFavoriteSongs()

        /**
         * Display error based on the error type.
         *
         *  @param errorType indicator of the type of error.
         */
        fun showError(errorType: Int)
    }

}
