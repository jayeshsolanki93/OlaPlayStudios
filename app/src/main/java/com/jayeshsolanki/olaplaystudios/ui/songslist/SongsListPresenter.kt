package com.jayeshsolanki.olaplaystudios.ui.songslist

import android.util.Log
import com.jayeshsolanki.olaplaystudios.data.model.Song
import com.jayeshsolanki.olaplaystudios.data.source.songs.SongsRepository
import com.jayeshsolanki.olaplaystudios.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SongsListPresenter @Inject
constructor(private val songsRepository: SongsRepository,
            private val songsListView: SongsListContract.View) : SongsListContract.Presenter {

    var TAG = this.javaClass.simpleName

    private val disposable: CompositeDisposable = CompositeDisposable()

    /**
     * Method injection is used here to safely reference `this` after the object is created.
     */
    @Inject
    internal fun setupListeners() {
        songsListView.setPresenter(this)
    }

    override fun subscribe() {
        loadSongsList()
    }

    override fun unSubscribe() {
        disposable.dispose()
    }

    override fun loadSongsList() {

        if (songsListView.viewType == Constants.ViewType.ALL.value) {
            songsRepository.getSongs()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { songs -> processSongs(songs) }, // onNext
                            { t -> handleError(t) } // onError
                    )
        } else {
            songsListView.loadFavoriteSongs()
        }
    }

    private fun processSongs(songs: List<Song>) {
        if (!songs.isEmpty()) {
            songsListView.showSongsList(songs)
        } else {
            processEmptySongs()
        }
    }

    private fun processEmptySongs() {
        songsListView.showError(1)
    }
    private fun handleError(t: Throwable) {
        Log.e(TAG, t.message)
        songsListView.showError(2)
    }

}
