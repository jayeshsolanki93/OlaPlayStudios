package com.jayeshsolanki.olaplaystudios.data.source.songs

import com.jayeshsolanki.olaplaystudios.data.api.SongsApi
import com.jayeshsolanki.olaplaystudios.data.model.Song

import io.reactivex.Observable

class SongsRepository(private val songsApi: SongsApi): SongsDataSource {

    override fun getSongs(): Observable<List<Song>> {
        return songsApi.getSongs()
    }

}
