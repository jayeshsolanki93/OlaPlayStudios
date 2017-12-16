package com.jayeshsolanki.olaplaystudios.ui.songslist

import com.jayeshsolanki.olaplaystudios.data.model.Song
import com.jayeshsolanki.olaplaystudios.data.source.songs.SongsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SongsListPresenter @Inject
constructor(private val songsRepository: SongsRepository,
            private val songsListView: SongsListContract.View) : SongsListContract.Presenter {

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
        songsRepository.getSongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { songs -> processSongs(songs) }, // onNext
                        { t -> handleError(t) } // onError
                )
    }

    private fun processSongs(songs: List<Song>) {
        if (!songs.isEmpty()) {
            songsListView.showSongsList(songs)
        } else {
            processEmptySongs()
        }
    }

    private fun processEmptySongs() {
        TODO("Show empty response with a toast on view.")
    }
    private fun handleError(t: Throwable) {
        TODO("Log and show some error on view")
    }

}
