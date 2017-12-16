package com.jayeshsolanki.olaplaystudios.data.source.songs.dagger

import com.jayeshsolanki.olaplaystudios.data.api.SongsApi
import com.jayeshsolanki.olaplaystudios.data.source.songs.SongsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal class SongsRepositoryModule {

    @Provides
    internal fun getSongsApi(retrofit: Retrofit) = retrofit.create(SongsApi::class.java)

    @Provides
    internal fun providePhotosRepository(songsApi: SongsApi) = SongsRepository(songsApi)

}
