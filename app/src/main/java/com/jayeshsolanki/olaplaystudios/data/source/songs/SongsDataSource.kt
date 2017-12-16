package com.jayeshsolanki.olaplaystudios.data.source.songs

import com.jayeshsolanki.olaplaystudios.data.model.Song

import io.reactivex.Observable

/**
 * Main entry point for accessing songs data.
 */
internal interface SongsDataSource {

    /**
     * Gets a list of songs (name, url, artists, cover image) observable.
     */
    fun getSongs(): Observable<List<Song>>
}
