package com.jayeshsolanki.olaplaystudios.data.api

import com.jayeshsolanki.olaplaystudios.data.model.Song

import io.reactivex.Observable
import retrofit2.http.GET

interface SongsApi {

    @GET("/studio")
    fun getSongs(): Observable<List<Song>>
}
